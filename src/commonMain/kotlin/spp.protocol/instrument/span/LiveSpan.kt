package spp.protocol.instrument.span

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import spp.protocol.instrument.InstrumentThrottle
import spp.protocol.instrument.LiveInstrument
import spp.protocol.instrument.LiveInstrumentType
import spp.protocol.instrument.LiveSourceLocation

/**
 * A live span represents a single unit of work in a program.
 *
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@Serializable
data class LiveSpan(
    val operationName: String,
    override val location: LiveSourceLocation,
    override val condition: String? = null,
    override val expiresAt: Long? = null,
    override val hitLimit: Int = -1,
    override val id: String? = null,
    override val applyImmediately: Boolean = false,
    override val applied: Boolean = false,
    override val pending: Boolean = false,
    override val throttle: InstrumentThrottle = InstrumentThrottle.DEFAULT,
    override val meta: Map<String, @Contextual Any> = emptyMap()
) : LiveInstrument() {
    override val type: LiveInstrumentType = LiveInstrumentType.SPAN

    /**
     * Specify explicitly so Kotlin doesn't override.
     */
    override fun hashCode(): Int = super.hashCode()
}
