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
package spp.protocol.artifact.metrics

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MetricTypeTest {

    @Test
    fun `test data object serialization`() {
        val metricType = MetricType("test_id")
        val serialized = metricType.toJson()
        val deserialized = MetricType(serialized)

        assertEquals(metricType, deserialized)
    }

    @Test
    fun `test ignoring realtime equality`() {
        val metricType = MetricType("test_id")
        val metricTypeRealtime = MetricType("test_id_realtime")

        assertNotEquals(metricType, metricTypeRealtime)
        assertTrue(metricType.equalsIgnoringRealtime(metricTypeRealtime))

        val metricType2 = MetricType("test_id2")
        assertNotEquals(metricType, metricType2)
        assertFalse(metricType.equalsIgnoringRealtime(metricType2))
        assertFalse(metricTypeRealtime.equalsIgnoringRealtime(metricType2))
    }

    @Test
    fun `test endpoint_avg renamed to endpoint_resp`() {
        val endpointAvg = MetricType("endpoint_avg")
        assertEquals("endpoint_avg", endpointAvg.metricId)
        assertEquals("endpoint_avg_realtime", endpointAvg.asRealtime().metricId)
        assertEquals("endpoint_resp_time", endpointAvg.getMetricId("9.0.0"))
        assertEquals("endpoint_resp_time_realtime", endpointAvg.asRealtime().getMetricId("9.0.0"))
        assertEquals("endpoint_avg", endpointAvg.getMetricId("8.0.0"))
        assertEquals("endpoint_avg_realtime", endpointAvg.asRealtime().getMetricId("8.0.0"))

        val endpointRespTime = MetricType("endpoint_resp_time")
        assertEquals("endpoint_resp_time", endpointRespTime.metricId)
        assertEquals("endpoint_resp_time_realtime", endpointRespTime.asRealtime().metricId)
        assertEquals("endpoint_resp_time", endpointRespTime.getMetricId("9.0.0"))
        assertEquals("endpoint_resp_time_realtime", endpointRespTime.asRealtime().getMetricId("9.0.0"))
        assertEquals("endpoint_avg", endpointRespTime.getMetricId("8.0.0"))
        assertEquals("endpoint_avg_realtime", endpointRespTime.asRealtime().getMetricId("8.0.0"))
    }
}
