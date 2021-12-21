package spp.protocol.developer

import kotlinx.serialization.Serializable

/**
 * Represents a developer.
 *
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@Serializable
data class Developer(
    val id: String,
    val accessToken: String? = null
)
