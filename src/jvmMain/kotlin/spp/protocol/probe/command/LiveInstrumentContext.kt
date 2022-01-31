/*
 * Source++, the open-source live coding platform.
 * Copyright (C) 2022 CodeBrig, Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
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
