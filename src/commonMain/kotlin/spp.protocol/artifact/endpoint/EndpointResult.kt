package spp.protocol.artifact.endpoint

import spp.protocol.Serializers
import spp.protocol.artifact.metrics.ArtifactSummarizedResult
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
data class EndpointResult(
    val timeFrame: QueryTimeFrame,
    @Serializable(with = Serializers.InstantKSerializer::class)
    val start: Instant,
    @Serializable(with = Serializers.InstantKSerializer::class)
    val stop: Instant,
    val step: String,
    val endpointMetrics: List<ArtifactSummarizedResult>
)
