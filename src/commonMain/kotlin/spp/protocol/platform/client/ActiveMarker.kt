package spp.protocol.platform.client

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class ActiveMarker(
    val markerId: String,
    val connectedAt: Long,
    val developerId: String,
    val meta: Map<String, @Contextual Any> = emptyMap()
)
