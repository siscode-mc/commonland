package commonland.core

import commonland.core.components.ClaimComponent
import commonland.core.containers.NaiveSpacialList
import commonland.core.containers.emptySpacialContainer
import commonland.utils.combine
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import java.util.*

class Claim (val parent : Claim?, val owner : UUID) : Space {
    val members: Set<UUID> = mutableSetOf()
    val children: NaiveSpacialList<Claim> = emptySpacialContainer()
    val components: NaiveSpacialList<ClaimComponent> = emptySpacialContainer()

    override var boundingBox: Box = calculateAABB()
        private set

    override fun contains(pos: BlockPos): Boolean {
        return components.get(pos) != null
    }

    override fun overlaps(aabb: Box): Boolean {
        return components.getOverlapping(aabb).isNotEmpty()
    }

    override fun overlaps(other: Space): Boolean {
        if (other is Claim) {
            val otherOverlap = other.components.getOverlapping(this.boundingBox)
            val thisOverlap = this.components.getOverlapping(other.boundingBox)
            for (itemA in otherOverlap){
                for(itemB in thisOverlap) {
                    if (itemA.overlaps(itemB)) return true;
                }
            }
            return false
        }

        //Since claims can overlap with components, but can't overlap with claims
        //Returning here should be fine.
        return false
    }

    private fun calculateAABB() : Box {
        return components.fold(components.first().boundingBox) { it,other -> it.combine(other.boundingBox) }
    }

}
