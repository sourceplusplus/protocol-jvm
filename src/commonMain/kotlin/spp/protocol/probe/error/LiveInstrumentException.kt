package spp.protocol.probe.error

class LiveInstrumentException(
    val errorType: ErrorType,
    message: String?
) : RuntimeException(message) {

    fun toEventBusException(): LiveInstrumentException {
        return LiveInstrumentException(errorType, "EventBusException:LiveInstrumentException[$errorType]: $message")
    }
//
//    @Synchronized
//    override fun fillInStackTrace(): Throwable {
//        return this
//    }

//    override fun toString(): String {
//        return localizedMessage
//    }

    enum class ErrorType {
        CLASS_NOT_FOUND,
        CONDITIONAL_FAILED
    }
}
