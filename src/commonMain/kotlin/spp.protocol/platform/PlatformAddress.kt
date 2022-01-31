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

    LIVE_BREAKPOINT_REMOVED("spp.platform.status.live-breakpoint-removed"),
    LIVE_BREAKPOINT_APPLIED("spp.platform.status.live-breakpoint-applied"),
    LIVE_BREAKPOINTS("spp.platform.status.live-breakpoints"),

    LIVE_LOG_REMOVED("spp.platform.status.live-log-removed"),
    LIVE_LOG_APPLIED("spp.platform.status.live-log-applied"),
    LIVE_LOGS("spp.platform.status.live-logs"),

    LIVE_METER_REMOVED("spp.platform.status.live-meter-removed"),
    LIVE_METER_APPLIED("spp.platform.status.live-meter-applied"),
    LIVE_METERS("spp.platform.status.live-meters"),

    LIVE_SPAN_REMOVED("spp.platform.status.live-span-removed"),
    LIVE_SPAN_APPLIED("spp.platform.status.live-span-applied"),
    LIVE_SPANS("spp.platform.status.live-spans"),

    MARKER_CONNECTED("spp.status.marker-connected"),
    MARKER_DISCONNECTED("spp.platform.status.marker-disconnected"),
    GENERATE_PROBE("spp.platform.generate-probe");
}
