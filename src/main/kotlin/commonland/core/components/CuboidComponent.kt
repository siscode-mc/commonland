package commonland.core.components

import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box

class CuboidComponent(val posA: BlockPos, val posB: BlockPos) : ClaimComponent() {
    override val boundingBox: Box
        get() = Box(posA, posB)

    override fun contains(pos: BlockPos): Boolean {
        return boundingBox.contains(pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble())
    }
}