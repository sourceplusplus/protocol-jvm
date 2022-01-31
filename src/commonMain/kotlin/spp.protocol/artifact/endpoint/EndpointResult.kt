/*
 * Source++, the open-source live coding platform.
 * Copyright (C) 2022 CodeBrig, Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
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
