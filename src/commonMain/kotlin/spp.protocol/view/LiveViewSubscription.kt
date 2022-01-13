package spp.protocol.view

import kotlinx.serialization.Serializable
import spp.protocol.instrument.LiveSourceLocation

/**
 * todo: description.
 *
 * @since 0.3.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@Serializable
data class LiveViewSubscription(
    val subscriptionId: String? = null,
    val entityIds: List<String>,
    val artifactQualifiedName: String, //todo: remove, use artifactLocation
    val artifactLocation: LiveSourceLocation,
    val liveViewConfig: LiveViewConfig
)
