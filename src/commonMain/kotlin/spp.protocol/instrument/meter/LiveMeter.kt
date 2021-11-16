package spp.protocol.instrument.meter

import spp.protocol.instrument.InstrumentThrottle
import spp.protocol.instrument.LiveInstrument
import spp.protocol.instrument.LiveInstrumentType
import spp.protocol.instrument.LiveSourceLocation
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

/**
 * todo: description.
 *
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@Serializable
data class LiveMeter(
    val meterName: String,
    val meterType: MeterType,
    val metricValue: MetricValue,
    override val location: LiveSourceLocation,
    override val condition: String? = null,
    override val expiresAt: Long? = null,
    override val hitLimit: Int = -1,
    override val id: String? = null,
    override val applyImmediately: Boolean = false,
    override val applied: Boolean = false,
    override val pending: Boolean = false,
    override val throttle: InstrumentThrottle? = null,
    override val meta: Map<String, @Contextual Any> = emptyMap()
) : LiveInstrument() {
    override val type: LiveInstrumentType = LiveInstrumentType.METER

    fun toMetricId(): String = "spp_" + meterType.name.lowercase() + "_" + id!!.replace("-", "_")

    /**
     * Specify explicitly so Kotlin doesn't override.
     */
    override fun hashCode(): Int = super.hashCode()
}
