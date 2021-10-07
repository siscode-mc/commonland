package commonland.core.serialization.json

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import commonland.core.components.ChunkComponent
import commonland.core.components.CuboidComponent

class MainSerializer {

    private val builder : GsonBuilder = GsonBuilder()
    val instance : Gson

    init {
        builder.registerTypeAdapter(ChunkComponent::class.java, ChunkComponentAdapter())
        builder.registerTypeAdapter(CuboidComponent::class.java, CuboidComponentAdapter())
        instance = builder.create()
    }
}