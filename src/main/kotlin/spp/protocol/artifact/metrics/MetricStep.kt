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
package spp.protocol.artifact.metrics

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

/**
 * todo: description.
 *
 * @since 0.7.6
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
enum class MetricStep {
    SECOND,
    MINUTE,
    HOUR,
    DAY;

    val seconds: Int by lazy {
        when (this) {
            SECOND -> 1
            MINUTE -> 60
            HOUR -> 3600
            DAY -> 86400
        }
    }

    val milliseconds: Int by lazy {
        seconds * 1000
    }

    val formatter: DateTimeFormatter by lazy {
        when (this) {
            SECOND -> DateTimeFormatter.ofPattern("yyyy-MM-dd HHmmss").withZone(ZoneOffset.UTC)
            MINUTE -> DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm").withZone(ZoneOffset.UTC)
            HOUR -> DateTimeFormatter.ofPattern("yyyy-MM-dd HH").withZone(ZoneOffset.UTC)
            DAY -> DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneOffset.UTC)
        }
    }

    val bucketFormatter: DateTimeFormatter by lazy {
        when (this) {
            SECOND -> DateTimeFormatter.ofPattern("yyyyMMddHHmmss").withZone(ZoneOffset.UTC)
            MINUTE -> DateTimeFormatter.ofPattern("yyyyMMddHHmm").withZone(ZoneOffset.UTC)
            HOUR -> DateTimeFormatter.ofPattern("yyyyMMddHH").withZone(ZoneOffset.UTC)
            DAY -> DateTimeFormatter.ofPattern("yyyyMMdd").withZone(ZoneOffset.UTC)
        }
    }

    fun toInstant(value: String): Instant {
        if (this == DAY) {
            return LocalDate.from(formatter.parse(value)).atStartOfDay(ZoneOffset.UTC).toInstant()
        }
        return Instant.from(formatter.parse(value))
    }
}
