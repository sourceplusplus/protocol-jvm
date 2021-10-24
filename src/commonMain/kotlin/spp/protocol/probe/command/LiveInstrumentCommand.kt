package spp.protocol.probe.command

import kotlinx.serialization.Serializable

@Serializable
data class LiveInstrumentCommand(
    var commandType: CommandType,
    var context: LiveInstrumentContext
) {

    @Serializable
    data class Response(
        var isSuccess: Boolean,
        var fault: String? = null,
        var timestamp: Long,
        var context: LiveInstrumentContext
    )

    enum class CommandType {
        ADD_LIVE_INSTRUMENT,
        REMOVE_LIVE_INSTRUMENT
    }
}
