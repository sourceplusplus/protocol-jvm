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
package spp.protocol.instrument

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import spp.protocol.instrument.location.LiveSourceLocation
import spp.protocol.instrument.meter.MeterType
import spp.protocol.instrument.meter.MetricValue
import spp.protocol.instrument.meter.MetricValueType
import java.util.*

class LiveMeterTest {

    @Test
    fun `valid metric id`() {
        try {
            LiveMeter(
                MeterType.COUNT,
                MetricValue(MetricValueType.NUMBER, "1"),
                location = LiveSourceLocation("location", -1),
                id = "test.the.id:1 2-3()"
            )
        } catch (e: Exception) {
            assertTrue(e.message!!.contains("Invalid meter id: 'test.the.id:1 2-3()'."))
        }

        assertTrue(LiveMeter.VALID_ID_PATTERN.matches("spp_" + UUID.randomUUID().toString().replace("-", "")))
        assertTrue(LiveMeter.VALID_ID_PATTERN.matches("spp_test_the_id_1_2_3"))
        assertFalse(LiveMeter.VALID_ID_PATTERN.matches("spp_test_the_id_1_2_3_"))
        assertFalse(LiveMeter.VALID_ID_PATTERN.matches("test_the_id"))
    }
}
