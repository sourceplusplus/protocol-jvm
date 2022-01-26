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
