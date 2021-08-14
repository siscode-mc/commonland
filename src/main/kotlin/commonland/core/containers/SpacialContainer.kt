package commonland.core.containers

import commonland.core.Space
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box

interface SpacialContainer<T> : Collection<T> where T : Space {
    fun get(pos: BlockPos): T?
    fun add(item: T)
    fun remove(item: T)
    fun getOverlapping(aabb : Box) : List<T>
    fun getOverlapping(space: Space) : List<T>
}
