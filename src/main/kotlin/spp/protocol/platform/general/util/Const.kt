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
package spp.protocol.platform.general.util

/**
 * Code taken from https://github.com/apache/skywalking/blob/v9.5.0/oap-server/server-core/src/main/java/org/apache/skywalking/oap/server/core/Const.java.
 */
object Const {
    const val NONE = 0
    const val SERVICE_ID_CONNECTOR = "."
    const val SERVICE_ID_PARSER_SPLIT = "\\."
    const val ID_CONNECTOR = "_"
    const val ID_PARSER_SPLIT = "\\_"
    const val RELATION_ID_CONNECTOR = "-"
    const val RELATION_ID_PARSER_SPLIT = "\\-"
    const val LINE = "-"
    const val UNDERSCORE = "_"
    const val COMMA = ","
    const val SPACE = " "
    const val KEY_VALUE_SPLIT = ","
    const val ARRAY_SPLIT = "|"
    const val ARRAY_PARSER_SPLIT = "\\|"
    const val USER_SERVICE_NAME = "User"
    const val USER_INSTANCE_NAME = "User"
    const val USER_ENDPOINT_NAME = "User"
    const val SEGMENT_SPAN_SPLIT = "S"
    const val UNKNOWN = "Unknown"
    const val EMPTY_STRING = ""
    const val POINT = "."
    const val DOUBLE_COLONS_SPLIT = "::"
    const val BLANK_ENTITY_NAME = "_blank"

    object TLS_MODE {
        const val NON_TLS = "NONE"
        const val M_TLS = "mTLS"
        const val TLS = "TLS"
    }
}
