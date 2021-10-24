package spp.protocol.platform.client

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class ActiveProcessor(
    val processorId: String,
    val connectedAt: Long,
    val meta: MutableMap<String, @Contextual Any> = mutableMapOf()
)
