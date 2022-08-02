/*
 * Source++, the open-source live coding platform.
 * Copyright (C) 2022 CodeBrig, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package spp.protocol.artifact.log

import kotlinx.datetime.Instant
import kotlinx.datetime.serializers.InstantIso8601Serializer
import kotlinx.serialization.Serializable
import spp.protocol.artifact.exception.LiveStackTrace

/**
 * todo: description.
 *
 * @since 0.2.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@Serializable
data class Log(
    @Serializable(with = InstantIso8601Serializer::class)
    val timestamp: Instant,
    val content: String,
    val level: String,
    val logger: String? = null,
    val thread: String? = null,
    val exception: LiveStackTrace? = null,
    val arguments: List<String> = listOf()
) {
    fun toFormattedMessage(): String {
        var arg = 0
        var formattedMessage = content
        while (formattedMessage.contains("{}")) {
            formattedMessage = formattedMessage.replaceFirst("{}", arguments[arg++])
        }
        return formattedMessage
    }
}
