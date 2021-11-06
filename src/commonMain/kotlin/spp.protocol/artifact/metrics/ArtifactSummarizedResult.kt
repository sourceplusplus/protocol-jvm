package spp.protocol.artifact.metrics

import spp.protocol.artifact.ArtifactQualifiedName
import spp.protocol.artifact.endpoint.EndpointType
import kotlinx.serialization.Serializable

/**
 * todo: description.
 *
 * @since 0.1.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@Serializable
data class ArtifactSummarizedResult(
    val artifactQualifiedName: ArtifactQualifiedName,
    val artifactSummarizedMetrics: List<ArtifactSummarizedMetrics>,
    val endpointType: EndpointType
)
