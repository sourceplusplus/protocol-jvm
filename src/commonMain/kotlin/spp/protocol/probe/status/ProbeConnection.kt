package spp.protocol.probe.status

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class ProbeConnection(
    var probeId: String,
    var connectionTime: Long,
    var meta: MutableMap<String, @Contextual Any> = mutableMapOf()
)
