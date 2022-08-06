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
package spp.protocol.artifact.exception

import kotlinx.serialization.Serializable
import spp.protocol.artifact.ArtifactNameUtils.getShortQualifiedClassName
import spp.protocol.instrument.variable.LiveVariable

/**
 * todo: description.
 *
 * @since 0.1.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@Serializable
data class LiveStackTraceElement(
    val method: String,
    val source: String,
    val column: Int? = null,
    val variables: MutableList<LiveVariable> = mutableListOf(),
    var sourceCode: String? = null
) {
    override fun toString(): String = toString(false)

    fun toString(shorten: Boolean): String {
        return if (shorten) {
            val shortName = "${shortQualifiedClassName()}.${methodName()}"
            val lineNumber = sourceAsLineNumber()
            if (lineNumber != null) {
                "at $shortName() line: $lineNumber"
            } else {
                "at $shortName($source)"
            }
        } else {
            "at $method($source)"
        }
    }
}

fun LiveStackTraceElement.sourceAsFilename(): String? {
    return if (source.contains(":")) {
        source.substring(0, source.indexOf(":"))
    } else {
        null
    }
}

fun LiveStackTraceElement.sourceAsLineNumber(): Int? {
    return if (source.contains(":")) {
        source.substring(source.lastIndexOf(":") + 1).toInt()
    } else {
        null
    }
}

fun LiveStackTraceElement.qualifiedClassName(): String {
    return method.substring(0, method.lastIndexOf("."))
}

fun LiveStackTraceElement.shortQualifiedClassName(): String {
    return getShortQualifiedClassName(qualifiedClassName())
}

fun LiveStackTraceElement.methodName(): String {
    return if (method.contains("$")) {
        method.substring(method.lastIndexOf(".") + 1).substringBefore("$")
    } else {
        method.substring(method.lastIndexOf(".") + 1)
    }
}
