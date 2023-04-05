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

import io.vertx.codegen.annotations.DataObject
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import spp.protocol.instrument.location.LiveSourceLocation
import spp.protocol.instrument.meter.MeterPartition
import spp.protocol.instrument.meter.MeterTag
import spp.protocol.instrument.meter.MeterType
import spp.protocol.instrument.meter.MetricValue
import spp.protocol.instrument.throttle.InstrumentThrottle

/**
 * A live meter represents a metric that is measured continuously over time.
 *
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@DataObject
data class LiveMeter(
    val meterType: MeterType,
    val metricValue: MetricValue? = null,
    val meterTags: List<MeterTag> = emptyList(),
    val meterPartitions: List<MeterPartition> = emptyList(),
    override val location: LiveSourceLocation,
    override val condition: String? = null,
    override val expiresAt: Long? = null,
    override val hitLimit: Int = -1,
    override val id: String? = null,
    override val applyImmediately: Boolean = false,
    override val applied: Boolean = false,
    override val pending: Boolean = false,
    override val throttle: InstrumentThrottle? = null,
    override val meta: Map<String, Any> = emptyMap()
) : LiveInstrument() {
    override val type: LiveInstrumentType = LiveInstrumentType.METER

    constructor(json: JsonObject) : this(
        meterType = MeterType.valueOf(json.getString("meterType")),
        metricValue = json.getJsonObject("metricValue")?.let { MetricValue(it) },
        meterTags = json.getJsonArray("meterTags")?.map { MeterTag(it as JsonObject) } ?: emptyList(),
        meterPartitions = json.getJsonArray("meterPartitions")?.map { MeterPartition(it as JsonObject) } ?: emptyList(),
        location = LiveSourceLocation(json.getJsonObject("location")),
        condition = json.getString("condition"),
        expiresAt = json.getLong("expiresAt"),
        hitLimit = json.getInteger("hitLimit") ?: -1,
        id = json.getString("id"),
        applyImmediately = json.getBoolean("applyImmediately") ?: false,
        applied = json.getBoolean("applied") ?: false,
        pending = json.getBoolean("pending") ?: false,
        throttle = json.getJsonObject("throttle")?.let { InstrumentThrottle(it) },
        meta = json.getJsonObject("meta")?.associate { it.key to it.value } ?: emptyMap()
    )

    override fun toJson(): JsonObject {
        val json = JsonObject()
        json.put("type", type.name)
        json.put("meterType", meterType.name)
        metricValue?.toJson()?.let { json.put("metricValue", it) }
        json.put("meterTags", JsonArray().apply { meterTags.forEach { add(it.toJson()) } })
        json.put("meterPartitions", JsonArray().apply { meterPartitions.forEach { add(it.toJson()) } })
        json.put("location", location.toJson())
        json.put("condition", condition)
        json.put("expiresAt", expiresAt)
        json.put("hitLimit", hitLimit)
        json.put("id", id)
        json.put("applyImmediately", applyImmediately)
        json.put("applied", applied)
        json.put("pending", pending)
        json.put("throttle", throttle?.toJson())
        json.put("meta", JsonObject(meta))
        return json
    }

    fun toMetricIdWithoutPrefix(): String = meterType.name.lowercase() + "_" +
            id!!.replace("-", "_").replace(" ", "_")

    fun toMetricId(): String = "spp_" + toMetricIdWithoutPrefix()

    /**
     * Specify explicitly so Kotlin doesn't override.
     */
    override fun equals(other: Any?): Boolean = super.equals(other)

    /**
     * Specify explicitly so Kotlin doesn't override.
     */
    override fun hashCode(): Int = super.hashCode()
}
