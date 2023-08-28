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
     * Uploads source code to the workspace.
     * @param sourceCode The source code to upload.
     */
    fun uploadSourceCode(workspaceId: String, sourceCode: JsonObject): Future<Void>

    /**
     * Uploads source code repository to the workspace.
     *
     * @param repository The source code repository to upload.
     */
    fun uploadRepository(workspaceId: String, repository: JsonObject): Future<Void>

    fun getArtifactInsights(workspaceId: String, artifact: ArtifactQualifiedName, types: JsonArray): Future<JsonObject>

    fun getProjectClasses(workspaceId: String, offset: Int, limit: Int): Future<JsonArray>

    fun getProjectFunctions(workspaceId: String, offset: Int, limit: Int): Future<JsonArray>

    fun getProjectEndpoints(workspaceId: String, offset: Int, limit: Int): Future<JsonArray>

    fun getFunctionCode(workspaceId: String, function: ArtifactQualifiedName): Future<JsonObject>
}
