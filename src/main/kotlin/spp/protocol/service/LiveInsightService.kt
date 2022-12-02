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
package spp.protocol.service

import io.vertx.codegen.annotations.GenIgnore
import io.vertx.codegen.annotations.ProxyGen
import io.vertx.codegen.annotations.VertxGen
import io.vertx.core.Future
import io.vertx.core.Vertx
import io.vertx.core.eventbus.DeliveryOptions
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import spp.protocol.artifact.ArtifactQualifiedName
import spp.protocol.insight.InsightWorkspace

@ProxyGen
@VertxGen
interface LiveInsightService {
    @GenIgnore
    companion object {
        @JvmStatic
        fun createProxy(vertx: Vertx, authToken: String? = null): LiveInsightService {
            val deliveryOptions = DeliveryOptions().apply {
                authToken?.let { addHeader("auth-token", it) }
            }
            return LiveInsightServiceVertxEBProxy(vertx, SourceServices.LIVE_INSIGHT, deliveryOptions)
        }
    }

    /**
     * Creates a new workspace where source code can be uploaded and analyzed.
     */
    fun createWorkspace(): Future<InsightWorkspace>
//    fun getWorkspace(workspaceId: String): Future<InsightWorkspace>
    fun getWorkspaces(): Future<List<InsightWorkspace>>
//    fun deleteWorkspace(workspaceId: String): Future<Void>

    /**
     * Uploads source code to the workspace.
     * @param workspaceId The workspace to upload the source code to.
     * @param sourceCode The source code to upload.
     */
    fun uploadSourceCode(workspaceId: String, sourceCode: JsonObject): Future<Void>

    /**
     * Uploads source code repository to the workspace.
     *
     * @param workspaceId The workspace to upload the source code to.
     * @param repository The source code repository to upload.
     */
    fun uploadRepository(workspaceId: String, repository: JsonObject): Future<Void>

    /**
     * Scans the workspace for insights.
     */
    fun scanWorkspace(workspaceId: String): Future<Void>

    /**
     * Gets the insights for the workspace.
     */
//    fun getInsights(workspaceId: String): Future<JsonArray>

    fun getArtifactInsights(workspaceId: String, artifact: ArtifactQualifiedName, types: JsonArray): Future<JsonObject>

//    fun getMethodInsights(workspaceId: String, method: ArtifactQualifiedName): Future<JsonObject>

    fun generateInsightGraph(workspaceId: String): Future<JsonObject>

//    @GenIgnore
//    suspend fun downloadInsightGraph(workspaceId: String): File {
//        generateInsightGraph(workspaceId).await()
//    }
}
