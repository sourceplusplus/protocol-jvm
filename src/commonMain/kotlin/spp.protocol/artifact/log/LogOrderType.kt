package spp.protocol.artifact.log

import spp.protocol.artifact.OrderType

/**
 * todo: description.
 *
 * @since 0.2.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
enum class LogOrderType : OrderType {
    NEWEST_LOGS,
    OLDEST_LOGS;

    //todo: not need to replace _LOGS?
    val id = name.replace("_LOGS", "").toLowerCase()
    override val description = id.toLowerCase().capitalize()
}
