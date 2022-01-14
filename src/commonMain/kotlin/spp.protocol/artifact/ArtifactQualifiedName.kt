package spp.protocol.artifact

import kotlinx.serialization.Serializable
import spp.protocol.Serializers.ArtifactTypeSerializer

/**
 * todo: description.
 *
 * @since 0.1.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@Serializable
data class ArtifactQualifiedName(
    val identifier: String,
    val commitId: String? = null,
    @Serializable(with = ArtifactTypeSerializer::class)
    val type: ArtifactType,
    val lineNumber: Int? = null,
    val operationName: String? = null //todo: only method artifacts need
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ArtifactQualifiedName) return false
        if (identifier != other.identifier) return false
        if (commitId != other.commitId) return false
        return true
    }

    override fun hashCode(): Int {
        var result = identifier.hashCode()
        result = 31 * result + (commitId?.hashCode() ?: 0)
        return result
    }
}
