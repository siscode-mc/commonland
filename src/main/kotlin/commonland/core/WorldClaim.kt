package commonland.core

import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.world.World
import java.util.*

class WorldClaim(owner: UUID, val world: World) : AbstractClaim(owner) {
    override fun notifyChildChanged(child: Claim) {
        this.children.notifyChanged(child)
    }

    override val boundingBox: Box
        get() = Box(-30_000_000.0,
                    world.bottomY+0.0,
                    -30_000_000.0,
                    30_000_000.0,
                    world.topY+0.0,
                    30_000_000.0)

    override fun contains(pos: BlockPos): Boolean = true

    fun getClaimsAt(pos: BlockPos) : List<Claim> {
        val list = mutableListOf<Claim>()
        var nextChild = this.children.get(pos)
        while (nextChild != null) {
            list.add(nextChild)
            nextChild = nextChild.children.get(pos)
        }
        return list
    }
}
