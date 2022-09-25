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
package spp.protocol.artifact

import io.vertx.codegen.annotations.DataObject
import io.vertx.core.json.JsonObject

/**
 * todo: description.
 *
 * @since 0.1.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@DataObject
data class ArtifactQualifiedName(
    val identifier: String,
    val commitId: String? = null,
    val type: ArtifactType,
    val lineNumber: Int? = null,
    val operationName: String? = null //todo: only method artifacts need
) {

    constructor(json: JsonObject) : this(
        json.getString("identifier"),
        json.getString("commitId"),
        ArtifactType.valueOf(json.getString("type")),
        json.getInteger("lineNumber"),
        json.getString("operationName")
    )

    fun toJson(): JsonObject {
        val json = JsonObject()
        json.put("identifier", identifier)
        json.put("commitId", commitId)
        json.put("type", type.name)
        json.put("lineNumber", lineNumber)
        json.put("operationName", operationName)
        return json
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ArtifactQualifiedName) return false
        if (identifier != other.identifier) return false
        if (commitId != other.commitId) return false
        return true
    }

    override fun hashCode(): Int {
        var result = identifier.hashCode()
        result = 31 * result + (commitId?.hashCode() ?: 0)
        return result
    }

    fun asParent(): ArtifactQualifiedName? {
        return when {
            type == ArtifactType.CLASS -> null

            type == ArtifactType.METHOD -> ArtifactQualifiedName(
                identifier.substringBefore("(").substringBeforeLast("."),
                commitId,
                ArtifactType.CLASS
            )

            type == ArtifactType.EXPRESSION && identifier.contains("(") -> ArtifactQualifiedName(
                identifier.substringBefore("#"),
                commitId,
                ArtifactType.METHOD
            )

            type == ArtifactType.EXPRESSION -> ArtifactQualifiedName(
                identifier.substringBefore("#"),
                commitId,
                ArtifactType.CLASS
            )

            else -> null
        }
    }

    fun isChildOf(other: ArtifactQualifiedName): Boolean {
        if (identifier == other.identifier) return false
        return identifier.substringBefore("#").startsWith(other.identifier)
    }

    fun isParentOf(other: ArtifactQualifiedName): Boolean {
        if (identifier == other.identifier) return false
        return other.identifier.substringBefore("#").startsWith(identifier)
    }

    fun toClass(): ArtifactQualifiedName? {
        return if (type == ArtifactType.CLASS) {
            this
        } else {
            var parent = asParent()
            while (parent != null && parent.type != ArtifactType.CLASS) {
                parent = parent.asParent()
            }
            parent
        }
    }
}
