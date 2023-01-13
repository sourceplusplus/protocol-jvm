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
package spp.protocol.marshall

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import io.vertx.core.json.Json
import io.vertx.core.json.JsonObject
import io.vertx.core.json.jackson.DatabindCodec
import spp.protocol.instrument.*
import spp.protocol.platform.auth.RolePermission
import java.time.Instant

/**
 * Used for marshalling and unmarshalling protocol messages.
 * Avoids using annotations and Jackson modules to keep probe-jvm dependencies simple.
 *
 * @since 0.2.1
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
object ProtocolMarshaller {
    init {
        try {
            DatabindCodec.mapper().enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)
            DatabindCodec.mapper().enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING)
            DatabindCodec.mapper().enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)
        } catch (ignore: Throwable) {
        }
    }

    @JvmStatic
    fun serializeInstant(value: Instant): String {
        return value.toEpochMilli().toString()
    }

    @JvmStatic
    fun deserializeInstant(value: String): Instant {
        return Instant.ofEpochMilli(value.toLong())
    }

    @JvmStatic
    fun serializeLiveInstrument(value: LiveInstrument): JsonObject {
        return JsonObject(Json.encode(value))
    }

    @JvmStatic
    fun deserializeLiveInstrument(value: JsonObject): LiveInstrument {
        return if (value.getString("type") == "BREAKPOINT") {
            LiveBreakpoint(value)
        } else if (value.getString("type") == "LOG") {
            LiveLog(value)
        } else if (value.getString("type") == "METER") {
            LiveMeter(value)
        } else if (value.getString("type") == "SPAN") {
            LiveSpan(value)
        } else {
            throw UnsupportedOperationException("Live instrument type: " + value.getString("type"))
        }
    }

    @JvmStatic
    fun serializeRolePermission(value: RolePermission): String {
        return value.name
    }

    @JvmStatic
    fun deserializeRolePermission(value: String): RolePermission {
        return RolePermission.valueOf(value)
    }
}
