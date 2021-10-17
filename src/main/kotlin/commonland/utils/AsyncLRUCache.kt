package commonland.utils

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import sun.misc.LRUCache
import kotlin.collections.HashMap

private class NodeQueue<T> {
    class Node<T>(val elem: T) {
        var prev: Node<T>? = null
        var next: Node<T>? = null
    }

    /**
     * invariants:
     *     (front == null) <-> (back == null)
     *     front?.next == null && back?.prev == null
     */
    var front: Node<T>? = null
    var back: Node<T>? = null

    fun addFront(node: Node<T>) {
        assert(node.next == null && node.prev == null) { "reusing linked node" }
        if (this.back == null) {
            this.back = node
            this.front = node
        }
        else {
            node.prev = this.front
            this.front = node
        }
    }

    fun addFront(elem: T) = addFront(Node(elem))

    fun removeBack(): T {
        val node = this.back ?: throw NoSuchElementException("Attempted pop from empty Queue")
        this.back = node.next
        if (this.back == null) this.front = null
        this.back!!.prev = null
        return node.elem
    }

    /**
     * Unlink the node from the list, bridging over its previous position and setting the prev and next to null
     *
     * This function does not check that the node is part of *this* list, so use with caution.
     */
    fun unlink(node: Node<T>) {
        if (node === front) {
            this.front = node.prev
            if (this.front == null) this.back = null
            node.prev = null  // node.next must always be null already
            return
        }
        if (node === back) {
            this.back = node.prev
            if (this.back == null) this.front = null
            node.next = null  // node.prev must always be null already
            return
        }
        // at this point, node must be in the middle of the list, so both next and prev are non-null
        node.next!!.prev = node.prev!!
        node.prev!!.next = node.next!!
        node.next = null
        node.prev = null
    }

}

class AsyncLRUCache<T> {
    private val nodemap: MutableMap<T, NodeQueue.Node<T>> = HashMap()
    private val queue: NodeQueue<T> = NodeQueue()
    private val channel: Channel<MessageType<T>> = Channel(capacity = Channel.UNLIMITED)

    sealed class MessageType<T>()
    class AddElem<T>(val elem: T, val result: CompletableDeferred<Unit>?): MessageType<T>()
    class Update<T>(val elem: T, val result: CompletableDeferred<Unit>?): MessageType<T>()
    class Pop<T>(val result: CompletableDeferred<T>): MessageType<T>()
    class Remove<T>(val elem: T, val result: CompletableDeferred<Unit>?): MessageType<T>()

    fun CoroutineScope.run() {
        launch {
            for (message in channel) {
                when(message) {
                    is AddElem -> _addElem(message.elem, message.result)
                    is Update -> _update(message.elem, message.result)
                    is Pop -> _pop(message.result)
                    is Remove -> _remove(message.elem, message.result)
                }
            }
        }
    }

    private fun _addElem(elem: T, result: CompletableDeferred<Unit>?) {
        if (nodemap.containsKey(elem)) {
            result?.completeExceptionally(IllegalStateException("Attempted to add element already in AsyncLRUCache"))
            return
        }
        val node = NodeQueue.Node(elem)
        queue.addFront(node)
        nodemap[elem] = node
        result?.complete(Unit)
    }

    private fun _update(elem: T, result: CompletableDeferred<Unit>?) {
        if(!nodemap.containsKey(elem)) {
            result?.completeExceptionally(IllegalStateException("Attempted to update element not present in AsyncLRUCache"))
            return
        }
        val node = nodemap[elem]!!
        queue.unlink(node)
        queue.addFront(node)
    }

    private fun _pop(result: CompletableDeferred<T>) {
        try {
            result.complete(queue.removeBack())
        } catch (e: NoSuchElementException) {
            result.completeExceptionally(e)
        }
    }

    private fun _remove(elem: T, result: CompletableDeferred<Unit>?) {
        if(!nodemap.containsKey(elem)) {
            result?.completeExceptionally(IllegalStateException("Attempted to remove element not present in AsyncLRUCache"))
            return
        }
        val node = nodemap[elem]!!
        queue.unlink(node)
        nodemap.remove(elem)
    }

    suspend fun addElement(elem: T) {
        val result = CompletableDeferred<Unit>()
        channel.trySend(AddElem(elem, result))
        result.await()
    }

    suspend fun update(elem: T) {
        val result = CompletableDeferred<Unit>()
        channel.trySend(Update(elem, result))
        result.await()
    }

    suspend fun unsafeForceRemove(elem: T) {
        val result = CompletableDeferred<Unit>()
        channel.trySend(Remove(elem, result))
        result.await()
    }

    suspend fun popOldest(): T {
        val result = CompletableDeferred<T>()
        channel.trySend(Pop(result))
        return result.await()
    }

    fun addElementAsync(elem: T) = channel.trySend(AddElem(elem, null)).getOrThrow()
    fun updateAsync(elem: T) = channel.trySend(Update(elem, null)).getOrThrow()
    fun unsafeForceRemoveAsync(elem: T) = channel.trySend(Remove(elem, null)).getOrThrow()
}
