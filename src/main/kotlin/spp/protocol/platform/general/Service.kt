/*
 * Source++, the continuous feedback platform for developers.
 * Copyright (C) 2022-2023 CodeBrig, Inc.
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
package spp.protocol.platform.general

import io.vertx.codegen.annotations.DataObject
import io.vertx.core.json.JsonObject
import spp.protocol.platform.general.util.IDManager

/**
 * Represents a service.
 *
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@DataObject
data class Service(
    val name: String,
    val group: String = "",
    val shortName: String? = null,
    val layers: List<String> = emptyList(),
    val normal: Boolean = true,
    val environment: String? = null,
    val commitId: String? = null
) {

    val id by lazy {
        if (commitId != null) {
            IDManager.ServiceID.buildId("$name|$environment|$commitId", normal)
        } else {
            IDManager.ServiceID.buildId(name, normal)
        }
    }

    constructor(json: JsonObject) : this(
        json.getString("name"),
        json.getString("group"),
        json.getString("shortName"),
        json.getJsonArray("layers").map { it as String },
        json.getBoolean("normal"),
        json.getString("environment"),
        json.getString("commitId")
    )

    fun toJson(): JsonObject {
        val json = JsonObject()
        json.put("name", name)
        json.put("group", group)
        json.put("shortName", shortName)
        json.put("layers", layers)
        json.put("normal", normal)
        json.put("environment", environment)
        json.put("commitId", commitId)
        return json
    }

    fun withEnvironment(environment: String?): Service {
        return copy(environment = environment)
    }

    fun withCommitId(commitId: String?): Service {
        return copy(commitId = commitId)
    }

    /**
     * Ensures all non-null fields are equal.
     */
    fun isSameLocation(other: Service): Boolean {
        if (name != other.name) return false
        if (group != other.group) return false
        if (shortName != null && shortName != other.shortName) return false
        if (layers != other.layers) return false
        if (normal != other.normal) return false
        if (environment != null && environment != other.environment) return false
        if (commitId != null && commitId != other.commitId) return false
        return true
    }

    fun withName(name: String?): Service {
        if (name == null) return this
        if (name.contains("|")) {
            val parts = name.split("|")
            return withName(parts[0])
                .withEnvironment(if (parts[1] != "null") parts[1] else null)
                .withCommitId(if (parts[2] != "null") parts[2] else null)
        }
        return copy(name = name)
    }

    fun withId(id: String?): Service {
        if (id == null) return this
        val definition = IDManager.ServiceID.analysisId(id)
        if (definition.name.contains("|")) {
            val parts = definition.name.split("|")
            return copy(
                name = parts[0],
                environment = if (parts[1] != "null") parts[1] else null,
                commitId = if (parts[2] != "null") parts[2] else null,
                normal = definition.isReal
            )
        }
        return copy(name = definition.name, normal = definition.isReal)
    }

    override fun toString(): String {
        return buildString {
            append("Service(")
            append("name=$name")
            if (group != "") append(", group=$group")
            if (shortName != null) append(", shortName=$shortName")
            if (layers.isNotEmpty()) append(", layers=$layers")
            if (!normal) append(", normal=$normal")
            if (environment != null) append(", environment=$environment")
            if (commitId != null) append(", commitId=$commitId")
            append(")")
        }
    }

    companion object {

        @JvmStatic
        fun fromId(id: String): Service {
            val definition = IDManager.ServiceID.analysisId(id)
            return fromName(definition.name)
        }

        @JvmStatic
        fun fromName(name: String): Service {
            if (name.contains("|")) {
                val parts = name.split("|")
                return fromName(parts[0])
                    .withEnvironment(if (parts[1] != "null") parts[1] else null)
                    .withCommitId(if (parts[2] != "null") parts[2] else null)
            }
            return Service(name = name)
        }

        @JvmStatic
        fun fromNameIfPresent(name: String?): Service? {
            return if (name != null) {
                fromName(name)
            } else {
                null
            }
        }
    }
}
