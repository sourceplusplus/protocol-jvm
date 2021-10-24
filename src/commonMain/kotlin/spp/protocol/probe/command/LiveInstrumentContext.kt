package spp.protocol.probe.command

import kotlinx.serialization.Serializable

//todo: treat this as a regular data class
@Serializable
data class LiveInstrumentContext(
    var instruments: MutableSet<String> = HashSet(),
    var locations: MutableSet<String> = HashSet()
)  {

//    @get:JsonIgnore
    val liveInstruments: List<String>
        get() = instruments.toList()

    fun addLiveInstrument(liveInstrument: Any): LiveInstrumentContext {
//        instruments.add(Json.encode(liveInstrument))
        return this
    }

    fun addLiveInstrument(liveInstrument: String): LiveInstrumentContext {
        instruments.add(liveInstrument)
        return this
    }

    fun addLiveInstruments(liveInstruments: Collection<String>): LiveInstrumentContext {
        instruments.addAll(liveInstruments)
        return this
    }

//    fun <T> getLiveInstrumentsCast(clazz: Class<T>): List<T> {
//        return instruments.stream().map { Json.decodeValue(it, clazz) }.collect(Collectors.toList())
//    }

    fun addLocation(location: Any) {
//        locations.add(Json.encode(location))
    }

//    fun <T> getLocationsCast(clazz: Class<T>): List<T> {
//        return locations.stream().map { Json.decodeValue(it, clazz) }.collect(Collectors.toList())
//    }
}
