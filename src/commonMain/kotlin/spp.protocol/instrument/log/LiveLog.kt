package spp.protocol.instrument.log

import spp.protocol.instrument.InstrumentThrottle
import spp.protocol.instrument.LiveInstrument
import spp.protocol.instrument.LiveInstrumentType
import spp.protocol.instrument.LiveSourceLocation
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * todo: description.
 *
 * @since 0.3.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@Serializable
@SerialName("LOG")
data class LiveLog(
    val logFormat: String,
    val logArguments: List<String> = emptyList(),
    override val location: LiveSourceLocation,
    override val condition: String? = null,
    override val expiresAt: Long? = null,
    override val hitLimit: Int = 1,
    override val id: String? = null,
    override val applyImmediately: Boolean = false,
    override val applied: Boolean = false,
    override val pending: Boolean = false,
    override val throttle: InstrumentThrottle = InstrumentThrottle.DEFAULT,
    override val meta: Map<String, @Contextual Any> = emptyMap()
) : LiveInstrument() {
    override val type: LiveInstrumentType = LiveInstrumentType.LOG

    /**
     * Specify explicitly so Kotlin doesn't override.
     */
    override fun hashCode(): Int {
        return super.hashCode()
    }
}
