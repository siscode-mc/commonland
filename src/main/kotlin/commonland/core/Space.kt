package commonland.core

import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box

interface Space {
    val boundingBox : Box
    fun contains(pos : BlockPos) : Boolean
    fun contains(aabb: Box) : Boolean
    fun contains(space: Space) : Boolean
    fun overlaps(aabb: Box) : Boolean
    fun overlaps(other: Space) : Boolean
}
