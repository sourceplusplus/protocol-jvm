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
package spp.protocol.artifact.trace

import io.vertx.codegen.annotations.DataObject
import io.vertx.core.json.JsonObject
import spp.protocol.artifact.ArtifactQualifiedName
import java.time.Instant

/**
 * todo: description.
 *
 * @since 0.1.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@DataObject
data class TraceResult(
    val artifactQualifiedName: ArtifactQualifiedName? = null,
    val artifactSimpleName: String? = null,
    val orderType: TraceOrderType,
    val start: Instant,
    val stop: Instant,
    val step: String,
    val traces: List<Trace>,
    val total: Int
) {

    constructor(json: JsonObject) : this(
        artifactQualifiedName = json.getJsonObject("artifactQualifiedName")?.let { ArtifactQualifiedName(it) },
        artifactSimpleName = json.getString("artifactSimpleName"),
        orderType = TraceOrderType.valueOf(json.getString("orderType")),
        start = Instant.parse(json.getString("start")),
        stop = Instant.parse(json.getString("stop")),
        step = json.getString("step"),
        traces = json.getJsonArray("traces").map { Trace(JsonObject.mapFrom(it)) },
        total = json.getInteger("total")
    )

    fun toJson(): JsonObject {
        return JsonObject.mapFrom(this)
    }

    fun mergeWith(traceResult: TraceResult): TraceResult {
        var result: TraceResult = traceResult
        require(artifactQualifiedName == result.artifactQualifiedName) { "Mismatching artifact qualified name" }
        require(orderType == result.orderType) { "Mismatching order type" }
        require(step == result.step) { "Mismatching step" }
        if (start < result.start) {
            result = result.copy(start = start)
        }
        if (stop > result.stop) {
            result = result.copy(stop = stop)
        }
        if (result.artifactSimpleName == null && artifactSimpleName != null) {
            result = result.copy(artifactSimpleName = artifactSimpleName)
        }
        val combinedTraces: MutableSet<Trace> = HashSet(traces)
        combinedTraces.addAll(result.traces)
        val finalTraces = ArrayList(combinedTraces).sortedWith(Comparator { t2: Trace, t1: Trace ->
            if (orderType == TraceOrderType.SLOWEST_TRACES) {
                return@Comparator t1.duration.compareTo(t2.duration)
            } else {
                return@Comparator t1.start.compareTo(t2.start)
            }
        })
        return result.copy(traces = finalTraces, total = finalTraces.size)
    }

    fun truncate(amount: Int): TraceResult {
        return if (traces.size > amount) {
            copy(traces = traces.subList(0, amount), total = traces.size)
        } else this
    }
}
