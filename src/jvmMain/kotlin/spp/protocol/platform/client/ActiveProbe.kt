package spp.protocol.platform.client

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class ActiveProbe(
    val probeId: String,
    val connectedAt: Long,
    val remotes: MutableList<String> = mutableListOf(),
    val meta: MutableMap<String, @Contextual Any> = mutableMapOf()
)
