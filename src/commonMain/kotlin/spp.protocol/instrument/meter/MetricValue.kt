package spp.protocol.instrument.meter

import kotlinx.serialization.Serializable

/**
 * todo: description.
 *
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@Serializable
data class MetricValue(
    val valueType: MetricValueType, //todo: can put mode in here instead of LiveMeter.meta
    val value: String
)
