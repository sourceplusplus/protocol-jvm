package spp.protocol.artifact.log

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import spp.protocol.Serializers
import spp.protocol.artifact.exception.LiveStackTrace

/**
 * todo: description.
 *
 * @since 0.2.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@Serializable
data class Log(
    @Serializable(with = Serializers.InstantKSerializer::class)
    val timestamp: Instant,
    val content: String,
    val level: String,
    val logger: String? = null,
    val thread: String? = null,
    val exception: LiveStackTrace? = null,
    val arguments: List<String> = listOf()
) {
    @Deprecated("Use toFormattedString() instead.", ReplaceWith("toFormattedString()"))
    fun getFormattedMessage(): String {
        return toFormattedMessage()
    }

    fun toFormattedMessage(): String {
        var arg = 0
        var formattedMessage = content
        while (formattedMessage.contains("{}")) {
            formattedMessage = formattedMessage.replaceFirst("{}", arguments[arg++])
        }
        return formattedMessage
    }
}
