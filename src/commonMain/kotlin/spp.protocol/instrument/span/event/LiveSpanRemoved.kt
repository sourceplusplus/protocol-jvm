package spp.protocol.instrument.span.event

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import spp.protocol.Serializers
import spp.protocol.artifact.exception.LiveStackTrace
import spp.protocol.instrument.LiveInstrumentEventType
import spp.protocol.instrument.TrackedLiveEvent

/**
 * todo: description.
 *
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@Serializable
data class LiveSpanRemoved(
    val spanId: String,
    @Serializable(with = Serializers.InstantKSerializer::class)
    override val occurredAt: Instant,
    val cause: LiveStackTrace? = null
) : TrackedLiveEvent {
    val eventType: LiveInstrumentEventType = LiveInstrumentEventType.SPAN_REMOVED
}
