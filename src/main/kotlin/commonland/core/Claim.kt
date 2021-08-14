package commonland.core

import commonland.core.components.ClaimComponent
import commonland.core.containers.NaiveSpacialList
import commonland.core.containers.emptySpacialContainer
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import java.util.*

class Claim (val parent : Claim?, val owner : UUID) : Space {
    val members: Set<UUID> = mutableSetOf()
    val children: NaiveSpacialList<Claim> = emptySpacialContainer()
    val components: NaiveSpacialList<ClaimComponent> = emptySpacialContainer()

    override val boundingBox: Box
        get() = TODO("Not yet implemented")

    override fun contains(pos: BlockPos): Boolean {
        TODO("Not yet implemented")
    }

    override fun overlaps(aabb: Box): Boolean {
        TODO("Not yet implemented")
    }

    override fun overlaps(other: Space): Boolean {
        TODO("Not yet implemented")
    }

}
