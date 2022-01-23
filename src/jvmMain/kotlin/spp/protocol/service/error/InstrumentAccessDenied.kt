package spp.protocol.service.error

import io.vertx.serviceproxy.ServiceException

/**
 * todo: description.
 *
 * @since 0.3.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
class InstrumentAccessDenied(val instrumentLocation: String, message: String? = null) : ServiceException(500, message) {

    fun toEventBusException(): InstrumentAccessDenied {
        return InstrumentAccessDenied(
            instrumentLocation, "EventBusException:InstrumentAccessDenied[$instrumentLocation]"
        )
    }
}
