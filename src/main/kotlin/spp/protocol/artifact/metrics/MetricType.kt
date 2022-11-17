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

import io.vertx.codegen.annotations.DataObject
import io.vertx.core.json.JsonObject
import java.util.function.Predicate

/**
 * todo: description.
 *
 * @since 0.1.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@DataObject
data class MetricType(val metricId: String) {

    companion object {
        val Endpoint_CPM = MetricType("endpoint_cpm")
        val Endpoint_RespTime = MetricType("endpoint_resp_time")
        val Endpoint_SLA = MetricType("endpoint_sla")
        val INSTANCE_JVM_CPU = MetricType("instance_jvm_cpu")
    }

    constructor(jsonObject: JsonObject) : this(
        jsonObject.getString("metricId")
    )

    fun toJson(): JsonObject {
        return JsonObject.mapFrom(this)
    }

    fun aliases(): List<Pair<String, Predicate<String>>>? {
        return when (metricId) {
            "endpoint_resp_time", "endpoint_avg" -> listOf(
                Pair("endpoint_resp_time", Predicate { it.startsWith("9") }),
                Pair("endpoint_avg", Predicate { !it.startsWith("9") }),
            )

            "endpoint_resp_time_realtime", "endpoint_avg_realtime" -> listOf(
                Pair("endpoint_resp_time_realtime", Predicate { it.startsWith("9") }),
                Pair("endpoint_avg_realtime", Predicate { !it.startsWith("9") }),
            )

            else -> null
        }
    }

    val simpleName: String
        get() = when (metricId.substringBefore("_realtime")) {
            Endpoint_CPM.metricId -> "Throughput"
            Endpoint_RespTime.metricId -> "Response"
            Endpoint_SLA.metricId -> "SLA"
            INSTANCE_JVM_CPU.metricId -> "JVM CPU"
            else -> "Unknown"
        }

    val isRealtime: Boolean = metricId.endsWith("_realtime")

    fun getMetricId(swVersion: String): String {
        aliases()?.forEach { (alias, predicate) ->
            if (predicate.test(swVersion)) {
                return alias
            }
        }
        return metricId
    }

    fun asRealtime(): MetricType {
        return MetricType(metricId + "_realtime")
    }

    fun equalsIgnoringRealtime(other: MetricType): Boolean {
        return metricId.substringBefore("_realtime") == other.metricId.substringBefore("_realtime")
    }
}
