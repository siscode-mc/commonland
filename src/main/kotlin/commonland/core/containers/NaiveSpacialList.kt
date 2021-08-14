package commonland.core.containers

import commonland.core.Space
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box

class NaiveSpacialList<T> : SpacialContainer<T>
        where T: Space {

    constructor() { }
    constructor(args: List<T>) {
        list.addAll(args)
    }

    private val list = mutableListOf<T>()

    override fun get(pos: BlockPos) = list.find { it.contains(pos) }

    override val size: Int
        get() = list.size

    override fun contains(element: T): Boolean =
        list.contains(element)


    override fun containsAll(elements: Collection<T>): Boolean =
        list.containsAll(elements)

    override fun isEmpty(): Boolean =
        list.isEmpty()

    override fun iterator(): Iterator<T> =
        list.iterator()

    override fun add(item: T) {
        list.add(item)
    }

    override fun remove(item: T) {
        list.remove(item)
    }

    override fun getOverlapping(aabb: Box): List<T> =
        list.filter { it.overlaps(aabb) }

    override fun getOverlapping(space: Space): List<T> =
        list.filter { it.overlaps(space) }

}

fun <T> emptySpacialContainer() where T : Space = NaiveSpacialList<T>()
fun <T> spacialSetOf(vararg args : T) where T : Space = NaiveSpacialList<T>(args.toList())