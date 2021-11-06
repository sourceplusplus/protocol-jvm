package spp.protocol.instrument.log.event

import spp.protocol.Serializers
import spp.protocol.artifact.log.LogResult
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
data class LiveLogHit(
    val logId: String,
    @Serializable(with = Serializers.InstantKSerializer::class)
    override val occurredAt: Instant,
    val serviceInstance: String,
    val service: String,
    val logResult: LogResult
) : TrackedLiveEvent {
    val eventType: LiveInstrumentEventType = LiveInstrumentEventType.LOG_HIT
}
