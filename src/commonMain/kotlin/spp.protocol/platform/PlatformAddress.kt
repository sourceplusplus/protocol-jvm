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
package spp.protocol.platform

enum class PlatformAddress(val address: String) {
    PROCESSOR_CONNECTED("spp.platform.status.processor-connected"),
    PROCESSOR_DISCONNECTED("spp.platform.status.processor-disconnected"),
    PROBE_CONNECTED("spp.platform.status.probe-connected"),
    PROBE_DISCONNECTED("spp.platform.status.probe-disconnected"),

    LIVE_INSTRUMENT_REMOVED("spp.platform.status.live-instrument-removed"),
    LIVE_INSTRUMENT_APPLIED("spp.platform.status.live-instrument-applied"),

    MARKER_CONNECTED("spp.status.marker-connected"),
    MARKER_DISCONNECTED("spp.platform.status.marker-disconnected"),
    GENERATE_PROBE("spp.platform.generate-probe");
}
