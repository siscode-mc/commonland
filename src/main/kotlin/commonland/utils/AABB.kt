package commonland.utils

import net.minecraft.util.math.Box
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
