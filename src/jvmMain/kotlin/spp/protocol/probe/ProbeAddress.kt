package spp.protocol.probe

enum class ProbeAddress(val address: String) {
    REMOTE_REGISTERED("spp.probe.status.remote-registered"),
    LIVE_BREAKPOINT_REMOTE("spp.probe.command.live-breakpoint-remote"),
    LIVE_LOG_REMOTE("spp.probe.command.live-log-remote"),
    LIVE_METER_REMOTE("spp.probe.command.live-meter-remote"),
    LIVE_SPAN_REMOTE("spp.probe.command.live-span-remote");
}
