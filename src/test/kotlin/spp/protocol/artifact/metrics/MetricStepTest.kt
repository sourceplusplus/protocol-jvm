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

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.Instant

class MetricStepTest {

    @Test
    fun testFromInstant() {
        assertEquals("2019-01-01 010203", MetricStep.SECOND.formatter.format(Instant.parse("2019-01-01T01:02:03Z")))
        assertEquals("2019-01-01 0102", MetricStep.MINUTE.formatter.format(Instant.parse("2019-01-01T01:02:03Z")))
        assertEquals("2019-01-01 01", MetricStep.HOUR.formatter.format(Instant.parse("2019-01-01T01:02:03Z")))
        assertEquals("2019-01-01", MetricStep.DAY.formatter.format(Instant.parse("2019-01-01T01:02:03Z")))
    }

    @Test
    fun testToInstant() {
        assertEquals(Instant.parse("2019-01-01T01:02:03Z"), MetricStep.SECOND.toInstant("2019-01-01 010203"))
        assertEquals(Instant.parse("2019-01-01T01:02:00Z"), MetricStep.MINUTE.toInstant("2019-01-01 0102"))
        assertEquals(Instant.parse("2019-01-01T01:00:00Z"), MetricStep.HOUR.toInstant("2019-01-01 01"))
        assertEquals(Instant.parse("2019-01-01T00:00:00Z"), MetricStep.DAY.toInstant("2019-01-01"))
    }
}
