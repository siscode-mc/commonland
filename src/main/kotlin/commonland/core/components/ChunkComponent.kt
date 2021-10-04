package commonland.core.components

import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.util.math.ChunkPos

class ChunkComponent(val chunk : ChunkPos) : ClaimComponent() {
    override val boundingBox: Box
        get() = Box((chunk.x * 16).toDouble()
                    , Double.MIN_VALUE
                    , (chunk.z * 16).toDouble()
                    , ((chunk.x + 1) * 16).toDouble()
                    , Double.MAX_VALUE
                    , ((chunk.z + 1) * 16).toDouble()
        )

    override fun contains(pos: BlockPos): Boolean {
        val chunkX = pos.x / 16
        val chunkZ = pos.z / 16

        return chunk.x == chunkX && chunk.z == chunkZ
    }
}