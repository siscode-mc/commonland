package commonland.core

import commonland.utils.iterBlocks
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box

interface Space {
    val boundingBox : Box
    fun contains(pos : BlockPos) : Boolean

    fun contains(aabb: Box) : Boolean
    fun contains(space: Space) : Boolean

    /**
     * This default implementation works in general, but is very slow.
     * it should be specialised as much as possible
     */
    fun overlaps(aabb: Box) : Boolean {
        for (pos in this.boundingBox.intersection(aabb).iterBlocks()) {
            if (this.contains(pos)) return true
        }
        return false
    }

    /**
     * This default implementation works in general, but is very slow.
     * it should be specialised as much as possible
     */
    fun overlaps(other: Space) : Boolean {
        for (pos in this.boundingBox.intersection(other.boundingBox).iterBlocks()) {
            if (this.contains(pos) && other.contains(pos)) return true
        }
        return false
    }
}
