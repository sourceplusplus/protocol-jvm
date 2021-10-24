package spp.protocol.processor.status

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class ProcessorConnection(
    var processorId: String,
    var connectionTime: Long,
    var meta: MutableMap<String, @Contextual Any> = mutableMapOf()
)
