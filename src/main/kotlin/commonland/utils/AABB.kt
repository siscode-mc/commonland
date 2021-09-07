package commonland.utils

import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min

fun Box.combine(other: Box) = Box(
    min(this.minX, other.minX),
    min(this.minY, other.minY),
    min(this.minZ, other.minZ),
    max(this.maxX, other.maxX),
    max(this.maxY, other.maxY),
    max(this.maxZ, other.maxZ)
)

fun Box.inside(parent: Box) : Boolean {
    if(   parent.minX < this.minX && parent.maxX > this.maxX
       && parent.minY < this.minY && parent.maxY > this.maxY
       && parent.minZ < this.minZ && parent.maxZ > this.maxZ)
        return true
    return false
}

fun Box.iterBlocks() = sequence {
    val xs = floor(this@iterBlocks.minX).toInt()..Math.ceil(this@iterBlocks.maxX).toInt()
    val ys = floor(this@iterBlocks.minY).toInt()..Math.ceil(this@iterBlocks.maxY).toInt()
    val zs = floor(this@iterBlocks.minZ).toInt()..Math.ceil(this@iterBlocks.maxZ).toInt()
    for (x in xs) for (y in ys) for (z in zs) {
        this.yield(BlockPos(x, y, z))
    }
}