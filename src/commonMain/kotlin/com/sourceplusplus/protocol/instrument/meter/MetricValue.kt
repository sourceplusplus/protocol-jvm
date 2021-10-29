package com.sourceplusplus.protocol.instrument.meter

import kotlinx.serialization.Serializable

/**
 * todo: description.
 *
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@Serializable
data class MetricValue(
    val valueType: MetricValueType,
    val number: String? = null,
    val supplier: String? = null
)
