package spp.protocol.probe.command

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.vertx.core.json.Json
import kotlinx.serialization.encodeToString
import spp.protocol.instrument.LiveInstrument
import spp.protocol.instrument.LiveSourceLocation
import spp.protocol.instrument.breakpoint.LiveBreakpoint
import spp.protocol.instrument.log.LiveLog
import spp.protocol.instrument.meter.LiveMeter
import spp.protocol.util.KSerializers
import java.io.Serializable
import java.util.stream.Collectors

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
        when (liveInstrument) {
            is LiveBreakpoint -> {
                instruments.add(KSerializers.json.encodeToString(liveInstrument))
            }
            is LiveLog -> {
                instruments.add(KSerializers.json.encodeToString(LiveLog.serializer(), liveInstrument))
            }
            is LiveMeter -> {
                instruments.add(KSerializers.json.encodeToString(LiveMeter.serializer(), liveInstrument))
            }
        }
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

    fun <T> getLiveInstrumentsCast(clazz: Class<T>): List<T> {
        return instruments.stream().map { Json.decodeValue(it, clazz) }.collect(Collectors.toList())
    }

    fun addLocation(location: LiveSourceLocation) {
        locations.add(KSerializers.json.encodeToString(LiveSourceLocation.serializer(), location))
    }

    fun <T> getLocationsCast(clazz: Class<T>): List<T> {
        return locations.stream().map { Json.decodeValue(it, clazz) }.collect(Collectors.toList())
    }
}
