package spp.protocol.instrument.log.event

import spp.protocol.Serializers
import spp.protocol.artifact.exception.LiveStackTrace
import spp.protocol.instrument.LiveInstrumentEventType
import spp.protocol.instrument.TrackedLiveEvent
import spp.protocol.instrument.log.LiveLog
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

/**
 * todo: description.
 *
 * @since 0.3.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@Serializable
data class LiveLogRemoved(
    val logId: String,
    @Serializable(with = Serializers.InstantKSerializer::class)
    override val occurredAt: Instant,
    val cause: LiveStackTrace? = null,
    val liveLog: LiveLog
) : TrackedLiveEvent {
    val eventType: LiveInstrumentEventType = LiveInstrumentEventType.LOG_REMOVED
}
