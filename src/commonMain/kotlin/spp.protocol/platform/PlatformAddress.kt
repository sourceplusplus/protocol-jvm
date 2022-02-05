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

object PlatformAddress {
    const val PROCESSOR_CONNECTED = "spp.platform.status.processor-connected"
    const val PROCESSOR_DISCONNECTED = "spp.platform.status.processor-disconnected"
    const val PROBE_CONNECTED = "spp.platform.status.probe-connected"
    const val PROBE_DISCONNECTED = "spp.platform.status.probe-disconnected"
    const val MARKER_CONNECTED = "spp.platform.status.marker-connected"
    const val MARKER_DISCONNECTED = "spp.platform.status.marker-disconnected"

    const val GENERATE_PROBE = "spp.platform.action.generate-probe"
}
