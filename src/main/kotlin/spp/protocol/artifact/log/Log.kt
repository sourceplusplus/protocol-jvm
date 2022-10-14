/*
 * Source++, the continuous feedback platform for developers.
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

import io.vertx.codegen.annotations.DataObject
import io.vertx.core.json.JsonObject
import spp.protocol.artifact.exception.LiveStackTrace
import java.time.Instant

/**
 * todo: description.
 *
 * @since 0.2.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@DataObject
data class Log(
    val timestamp: Instant,
    val content: String,
    val level: String,
    val logger: String? = null,
    val thread: String? = null,
    val exception: LiveStackTrace? = null,
    val arguments: List<String> = listOf()
) {

    constructor(json: JsonObject) : this(
        Instant.parse(json.getString("timestamp")),
        json.getString("content"),
        json.getString("level"),
        json.getString("logger"),
        json.getString("thread"),
        if (json.getValue("exception") != null) LiveStackTrace(json.getJsonObject("exception")) else null,
        json.getJsonArray("arguments").map { it.toString() }
    )

    fun toFormattedMessage(): String {
        var arg = 0
        var formattedMessage = content
        while (formattedMessage.contains("{}")) {
            formattedMessage = formattedMessage.replaceFirst("{}", arguments[arg++])
        }
        return formattedMessage
    }
}
