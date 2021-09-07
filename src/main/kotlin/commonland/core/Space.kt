package commonland.core

import commonland.utils.inside
import commonland.utils.iterBlocks
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box

interface Space {
    val boundingBox : Box
    fun contains(pos : BlockPos) : Boolean

    /**
     * This default implementation works in general, but is very slow.
     * it should be specialised as much as possible
     */
    fun contains(aabb: Box) : Boolean {
        if(!aabb.inside(this.boundingBox)) return false
        for (pos in aabb.iterBlocks()) {
            if (!this.contains(pos)) return false
        }
        return true
    }

    /**
     * This default implementation works in general, but is very slow.
     * it should be specialised as much as possible
     */
    fun contains(space: Space) : Boolean {
        if(!space.boundingBox.inside(this.boundingBox)) return false
        for (pos in space.boundingBox.iterBlocks()) {
            if (space.contains(pos) && !this.contains(pos)) return false
        }
        return true
    }

    /**
     * This default implementation works in general, but is very slow.
     * it should be specialised as much as possible
     */
    fun overlaps(aabb: Box) : Boolean {
        if(!this.boundingBox.intersects(aabb)) return false
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
        if(!this.boundingBox.intersects(other.boundingBox)) return false
        for (pos in this.boundingBox.intersection(other.boundingBox).iterBlocks()) {
            if (this.contains(pos) && other.contains(pos)) return true
        }
        return false
    }

    fun getSizeHint() : Int {
        return 0
    }
}
