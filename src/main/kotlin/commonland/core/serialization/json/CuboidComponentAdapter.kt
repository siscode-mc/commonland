package commonland.core.serialization.json

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import commonland.core.components.CuboidComponent
import net.minecraft.util.math.BlockPos

class CuboidComponentAdapter : TypeAdapter<CuboidComponent>() {
    val gson : Gson = Gson()

    override fun write(out: JsonWriter?, value: CuboidComponent?) {
        val jsonValue = if (value != null) "[${value.posA.x},${value.posA.y},${value.posA.z},${value.posB.x},${value.posB.y},${value.posB.z}]" else "[]"
        out?.jsonValue(jsonValue)
    }

    override fun read(p0: JsonReader?): CuboidComponent {
        val ints: Array<Int> = gson.fromJson(p0, Array<Int>::class.java)
        assert(ints.size == 6)
        val posA = BlockPos(ints[0], ints[1], ints[2])
        val posB = BlockPos(ints[3], ints[4], ints[5])
        return CuboidComponent(posA,posB)
    }
}