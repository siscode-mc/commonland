package commonland.core

import commonland.core.containers.SpacialContainer
import commonland.core.containers.emptySpacialContainer
import commonland.utils.inside
import java.util.*

abstract class AbstractClaim (val owner: UUID) : Space {
    val members: Set<UUID> = mutableSetOf()
    val children: SpacialContainer<Claim> = emptySpacialContainer()

    fun addChild(claim: Claim) : Boolean {
        // Rules for sub-claim registry:
        // The sub-claim must be inside the current claim completely
        // Note: This currently uses naive AABB logic
        if(!claim.boundingBox.inside(this.boundingBox)) return false

        // There must not be any overlap with other claims on the same level of hierarchy
        val overlaps = children.getOverlapping(claim)
        if(overlaps.isNotEmpty()) return false
        children.add(claim)
        return true
    }

    abstract fun notifyChildChanged(child:Claim) : Unit
}