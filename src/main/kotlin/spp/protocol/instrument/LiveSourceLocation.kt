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
package spp.protocol.instrument

import io.vertx.codegen.annotations.DataObject
import io.vertx.core.json.JsonObject

/**
 * todo: description.
 *
 * @since 0.3.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@DataObject
data class LiveSourceLocation @JvmOverloads constructor(
    val source: String,
    val line: Int = -1,
    val service: String? = null, //todo: can use Service
    val serviceInstance: String? = null, //todo: fully impl
    val commitId: String? = null, //todo: impl
    val fileChecksum: String? = null, //todo: impl
    //val language: ArtifactLanguage? = null, //todo: impl
) : Comparable<LiveSourceLocation> {

    constructor(json: JsonObject) : this(
        source = json.getString("source"),
        line = json.getInteger("line"),
        service = json.getString("service"),
        serviceInstance = json.getString("serviceInstance"),
        commitId = json.getString("commitId"),
        fileChecksum = json.getString("fileChecksum")
    )

    fun toJson(): JsonObject {
        val json = JsonObject()
        json.put("source", source)
        json.put("line", line)
        json.put("service", service)
        json.put("serviceInstance", serviceInstance)
        json.put("commitId", commitId)
        json.put("fileChecksum", fileChecksum)
        return json
    }

    override fun compareTo(other: LiveSourceLocation): Int {
        val sourceCompare = source.compareTo(other.source)
        if (sourceCompare != 0) return sourceCompare
        return line.compareTo(other.line)
    }

    fun isSameLocation(other: LiveSourceLocation): Boolean {
        if (source != other.source) return false
        if (line != other.line && line != -1 && other.line != -1) return false //-1 is wildcard
        if (service != other.service) return false
        if (serviceInstance != other.serviceInstance) return false
        if (commitId != other.commitId) return false
        if (fileChecksum != other.fileChecksum) return false
        return true
    }
}
