package com.sourceplusplus.protocol.instrument.meter

import com.sourceplusplus.protocol.instrument.InstrumentThrottle
import com.sourceplusplus.protocol.instrument.LiveInstrument
import com.sourceplusplus.protocol.instrument.LiveInstrumentType
import com.sourceplusplus.protocol.instrument.LiveSourceLocation
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
    override val throttle: InstrumentThrottle = InstrumentThrottle.DEFAULT,
    override val meta: Map<String, @Contextual Any> = emptyMap()
) : LiveInstrument() {
    override val type: LiveInstrumentType = LiveInstrumentType.METER

    fun toMetricId(): String = "spp_" + meterType.name.lowercase() + "_" + id!!.replace("-", "_")

    /**
     * Specify explicitly so Kotlin doesn't override.
     */
    override fun hashCode(): Int = super.hashCode()
}
