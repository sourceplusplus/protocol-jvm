package spp.protocol.processor

enum class ProcessorAddress(val address: String) {
    BREAKPOINT_HIT("spp.provider.status.breakpoint-hit"),
    LOG_HIT("spp.provider.status.log-hit"),
    SET_LOG_PUBLISH_RATE_LIMIT("spp.provider.setting.log-publish-rate-limit");
}
