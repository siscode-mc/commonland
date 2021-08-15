package commonland.core

import commonland.core.components.ClaimComponent
import commonland.core.containers.SpacialContainer
import commonland.core.containers.emptySpacialContainer
import commonland.utils.combine
import commonland.utils.inside
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import java.util.*

class Claim (owner : UUID, val parent : AbstractClaim) : AbstractClaim(owner) {

    val components: SpacialContainer<ClaimComponent> = emptySpacialContainer()

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
        return this.components.any { it.overlaps(other) }
    }

    // Note: A claim can only modify its own shape based on components.
    fun addComponent(component: ClaimComponent) : Boolean {
        // Rules for component registry:
        // - A component must only be added if:
        //    - It overlaps with the existing claim, if any exists (expansion)
        if (!this.overlaps(component)) return false

        //    - It is inside the parent claim completely (partitioning)
        if (!component.boundingBox.inside(this.parent.boundingBox)) return false
        //TODO: specific smart Space.inside function that's specified for Claim.

        //    - It does not overlap any sibling claims (conflict)
        if (this.parent.children.getOverlapping(component).any {it != this}) return false

        components.add(component)
        boundingBox = calculateAABB()
        return true
    }

    override fun notifyChildChanged(child: Claim) {
        TODO("Not Implemented")
    }

    private fun calculateAABB() : Box {
        return components.fold(components.first().boundingBox) { it,other -> it.combine(other.boundingBox) }
    }

}
