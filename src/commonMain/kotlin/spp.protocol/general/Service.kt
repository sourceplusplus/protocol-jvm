package spp.protocol.general

import kotlinx.serialization.Serializable

/**
 * Represents a service.
 *
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@Serializable
data class Service(
    val id: String,
    val name: String,
    val group: String? = null
)
