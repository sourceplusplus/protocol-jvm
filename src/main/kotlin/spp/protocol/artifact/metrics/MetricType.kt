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
        val Service_SLA = MetricType("service_sla")
        val Service_CPM = MetricType("service_cpm")
        val Service_RespTime_AVG = MetricType("service_resp_time")
        val Service_RespTime_Percentiles = MetricType("service_percentile")

        val Service_Instance_SLA = MetricType("service_instance_sla")
        val Service_Instance_CPM = MetricType("service_instance_cpm")
        val Service_Instance_RespTime_AVG = MetricType("service_instance_resp_time")

        val Endpoint_SLA = MetricType("endpoint_sla")
        val Endpoint_CPM = MetricType("endpoint_cpm")
        val Endpoint_RespTime_AVG = MetricType("endpoint_resp_time")
        val Endpoint_RespTime_Percentiles = MetricType("endpoint_percentile")

        val INSTANCE_JVM_CPU = MetricType("instance_jvm_cpu")

        val ALL = listOf(
            Service_SLA,
            Service_CPM,
            Service_RespTime_AVG,
            Service_RespTime_Percentiles,
            Service_Instance_SLA,
            Service_Instance_CPM,
            Service_Instance_RespTime_AVG,
            Endpoint_SLA,
            Endpoint_CPM,
            Endpoint_RespTime_AVG,
            Endpoint_RespTime_Percentiles,
            INSTANCE_JVM_CPU
        )
    }

    constructor(jsonObject: JsonObject) : this(
        jsonObject.getString("metricId")
    )

    fun toJson(): JsonObject {
        return JsonObject.mapFrom(this)
    }

    @Deprecated("v9.0.0+ only")
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
            Service_CPM.metricId -> "Throughput"
            Service_Instance_CPM.metricId -> "Throughput"
            Endpoint_CPM.metricId -> "Throughput"

            Service_RespTime_AVG.metricId -> "Response"
            Service_Instance_RespTime_AVG.metricId -> "Response"
            Endpoint_RespTime_AVG.metricId -> "Response"
            Service_RespTime_Percentiles.metricId -> "Response"
            Endpoint_RespTime_Percentiles.metricId -> "Response"

            Service_SLA.metricId -> "SLA"
            Service_Instance_SLA.metricId -> "SLA"
            Endpoint_SLA.metricId -> "SLA"

            INSTANCE_JVM_CPU.metricId -> "JVM CPU"
            else -> "Unknown"
        }

    val unitType: String
        get() = when (metricId.substringBefore("_realtime")) {
            Service_CPM.metricId -> "req/min"
            Service_Instance_CPM.metricId -> "req/min"
            Endpoint_CPM.metricId -> "req/min"

            Service_RespTime_AVG.metricId -> "ms"
            Service_Instance_RespTime_AVG.metricId -> "ms"
            Endpoint_RespTime_AVG.metricId -> "ms"
            Service_RespTime_Percentiles.metricId -> "ms"
            Endpoint_RespTime_Percentiles.metricId -> "ms"

            Service_SLA.metricId -> "%"
            Service_Instance_SLA.metricId -> "%"
            Endpoint_SLA.metricId -> "%"

            INSTANCE_JVM_CPU.metricId -> "%"
            else -> "Unknown"
        }

    val isRealtime: Boolean = metricId.endsWith("_realtime")

    @Deprecated("use metricId instead", ReplaceWith("metricId"))
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
