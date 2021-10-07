package commonland.core.serialization.json

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import commonland.core.components.ChunkComponent
import net.minecraft.util.math.ChunkPos

class ChunkComponentAdapter : TypeAdapter<ChunkComponent>() {
    val gson : Gson = Gson()
    override fun write(out: JsonWriter?, value: ChunkComponent?) {
        val jsonValue = if (value != null) "[${value.chunk.x},${value.chunk.z}]" else "[]"
        out?.jsonValue(jsonValue)
    }

    override fun read(p0: JsonReader): ChunkComponent {
        val ints: Array<Int> = gson.fromJson(p0, Array<Int>::class.java)
        assert(ints.size == 2)
        return ChunkComponent(ChunkPos(ints[0], ints[1]))
    }
}