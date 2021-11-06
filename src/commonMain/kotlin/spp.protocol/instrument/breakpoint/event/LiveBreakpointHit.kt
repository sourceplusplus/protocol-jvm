package spp.protocol.instrument.breakpoint.event

import spp.protocol.Serializers
import spp.protocol.artifact.exception.LiveStackTrace
import spp.protocol.instrument.LiveInstrumentEventType
import spp.protocol.instrument.TrackedLiveEvent
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

/**
 * todo: description.
 *
 * @since 0.3.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@Serializable
data class LiveBreakpointHit(
    val breakpointId: String,
    val traceId: String,
    @Serializable(with = Serializers.InstantKSerializer::class)
    override val occurredAt: Instant,
    val host: String,
    val application: String,
    val stackTrace: LiveStackTrace
) : TrackedLiveEvent {
    val eventType: LiveInstrumentEventType = LiveInstrumentEventType.BREAKPOINT_HIT
}
