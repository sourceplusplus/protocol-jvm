package spp.protocol.artifact.metrics

import spp.protocol.Serializers
import spp.protocol.artifact.QueryTimeFrame
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

/**
 * todo: description.
 *
 * @since 0.1.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@Serializable
data class ArtifactMetricResult(
    val artifactQualifiedName: String,
    val timeFrame: QueryTimeFrame,
    val focus: MetricType,
    @Serializable(with = Serializers.InstantKSerializer::class)
    val start: Instant,
    @Serializable(with = Serializers.InstantKSerializer::class)
    val stop: Instant,
    val step: String,
    val artifactMetrics: List<ArtifactMetrics>,
    val pushMode: Boolean = false
)
