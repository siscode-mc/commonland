package commonland.core.io

import commonland.core.AbstractClaim
import commonland.core.WorldClaim
import net.minecraft.world.World

interface Provider {
    fun getWorldClaim(world: World) : WorldClaim
    fun save(claim: AbstractClaim) : Unit
    fun saveAll() : Unit
}