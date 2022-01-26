package spp.protocol.probe.command

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.vertx.core.json.Json
import spp.protocol.instrument.LiveInstrument
import spp.protocol.instrument.LiveSourceLocation
import java.io.Serializable

//todo: treat this as a regular data class
@JsonIgnoreProperties(ignoreUnknown = true)
data class LiveInstrumentContext(
    var instruments: MutableSet<String> = HashSet(),
    var locations: MutableSet<String> = HashSet()
) : Serializable {

    @get:JsonIgnore
    val liveInstruments: List<String>
        get() = instruments.toList()

    fun addLiveInstrument(liveInstrument: LiveInstrument): LiveInstrumentContext {
        instruments.add(Json.encode(liveInstrument))
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

    fun addLocation(location: LiveSourceLocation) {
        locations.add(Json.encode(location))
    }
}
