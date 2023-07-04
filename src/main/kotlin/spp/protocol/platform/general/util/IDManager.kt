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
package spp.protocol.platform.general.util

import com.google.common.base.Charsets
import com.google.common.hash.Hashing
import java.nio.charset.StandardCharsets
import java.util.*

/**
 * Taken from https://github.com/apache/skywalking/blob/v9.5.0/oap-server/server-core/src/main/java/org/apache/skywalking/oap/server/core/analysis/IDManager.java.
 */
object IDManager {

    /**
     * @param text normal literal string
     * @return Base64 encoded UTF-8 string
     */
    private fun encode(text: String?): String {
        return String(Base64.getEncoder().encode(text!!.toByteArray(StandardCharsets.UTF_8)), StandardCharsets.UTF_8)
    }

    /**
     * @param base64text Base64 encoded UTF-8 string
     * @return normal literal string
     */
    private fun decode(base64text: String): String {
        return String(Base64.getDecoder().decode(base64text), StandardCharsets.UTF_8)
    }

    /**
     * Service ID related functions.
     */
    object ServiceID {

        /**
         * @param name     service name
         * @param isNormal `true` represents this service is detected by an agent. `false` represents this service is
         * conjectured by telemetry data collected from agents on/in the `normal` service.
         */
        fun buildId(name: String, isNormal: Boolean): String {
            var name = name
            if (name.isBlank()) {
                name = Const.BLANK_ENTITY_NAME
            }
            return encode(name) + Const.SERVICE_ID_CONNECTOR + if (isNormal) 1 else 0
        }

        /**
         * @return service ID object decoded from [.buildId] result
         */
        fun analysisId(id: String): ServiceIDDefinition {
            val strings: Array<String> =
                id.split(Const.SERVICE_ID_PARSER_SPLIT.toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
            require(strings.size == 2) { "Can't split service id into 2 parts, $id" }
            return ServiceIDDefinition(
                decode(strings[0]),
                strings[1].toInt() == 1
            )
        }

        /**
         * @return encoded service relation id
         */
        fun buildRelationId(define: ServiceRelationDefine): String {
            return (define.sourceId + Const.RELATION_ID_CONNECTOR).toString() + define.destId
        }

        /**
         * @return service relation ID object decoded from [.buildRelationId] result
         */
        fun analysisRelationId(entityId: String): ServiceRelationDefine {
            val parts: Array<String> =
                entityId.split(Const.RELATION_ID_PARSER_SPLIT.toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
            require(parts.size == 2) { "Illegal Service Relation entity id" }
            return ServiceRelationDefine(parts[0], parts[1])
        }

        data class ServiceIDDefinition(
            val name: String,

            /**
             * TRUE means an agent installed or directly detected service. FALSE means a conjectural service
             */
            val isReal: Boolean = false
        )

        data class ServiceRelationDefine(
            val sourceId: String? = null,
            val destId: String? = null
        )
    }

    /**
     * Service Instance ID related functions.
     */
    object ServiceInstanceID {

        /**
         * @param serviceId built by [ServiceID.buildId]
         * @return service instance id
         */
        fun buildId(serviceId: String, instanceName: String): String {
            var instanceName = instanceName
            if (instanceName.isBlank()) {
                instanceName = Const.BLANK_ENTITY_NAME
            }
            return (serviceId + Const.ID_CONNECTOR) + encode(instanceName)
        }

        /**
         * @return service instance id object decoded from [.buildId] result
         */
        fun analysisId(id: String): InstanceIDDefinition {
            val strings: Array<String> = id.split(Const.ID_PARSER_SPLIT.toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
            require(strings.size == 2) { "Can't split instance id into 2 parts, $id" }
            return InstanceIDDefinition(
                strings[0],
                decode(strings[1])
            )
        }

        /**
         * @return encoded service instance relation id
         */
        fun buildRelationId(define: ServiceInstanceRelationDefine): String {
            return (define.sourceId + Const.RELATION_ID_CONNECTOR).toString() + define.destId
        }

        /**
         * @return service instance relation ID object decoded from [.buildRelationId]
         * result
         */
        fun analysisRelationId(entityId: String): ServiceInstanceRelationDefine {
            val parts: Array<String> =
                entityId.split(Const.RELATION_ID_PARSER_SPLIT.toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
            require(parts.size == 2) { "Illegal Service Instance Relation entity id" }
            return ServiceInstanceRelationDefine(parts[0], parts[1])
        }

        data class InstanceIDDefinition(
            /**
             * Built by [ServiceID.buildId]
             */
            private val serviceId: String? = null,
            private val name: String? = null
        )

        data class ServiceInstanceRelationDefine(
            /**
             * Built by [ServiceInstanceID.buildId]
             */
            val sourceId: String? = null,

            /**
             * Built by [ServiceInstanceID.buildId]
             */
            val destId: String? = null
        )
    }

    /**
     * Endpoint ID related functions.
     */
    object EndpointID {

        /**
         * @param serviceId built by [ServiceID.buildId]
         * @return endpoint id
         */
        fun buildId(serviceId: String, endpointName: String): String {
            var endpointName = endpointName
            if (endpointName.isBlank()) {
                endpointName = Const.BLANK_ENTITY_NAME
            }
            return (serviceId + Const.ID_CONNECTOR) + encode(endpointName)
        }

        /**
         * @return Endpoint id object decoded from [.buildId] result.
         */
        fun analysisId(id: String): EndpointIDDefinition {
            val strings: Array<String> = id.split(Const.ID_PARSER_SPLIT.toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
            require(strings.size == 2) { "Can't split endpoint id into 2 parts, $id" }
            return EndpointIDDefinition(
                strings[0],
                decode(strings[1])
            )
        }

        /**
         * @return the endpoint relationship string id.
         */
        fun buildRelationId(define: EndpointRelationDefine): String {
            return (((define.sourceServiceId
                    + Const.RELATION_ID_CONNECTOR
                    ) + encode(define.source)
                    + Const.RELATION_ID_CONNECTOR
                    ) + define.destServiceId
                    + Const.RELATION_ID_CONNECTOR
                    ) + encode(define.dest)
        }

        /**
         * @return endpoint relation ID object decoded from [.buildRelationId] result
         */
        fun analysisRelationId(entityId: String): EndpointRelationDefine {
            val parts: Array<String> =
                entityId.split(Const.RELATION_ID_PARSER_SPLIT.toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
            require(parts.size == 4) { "Illegal endpoint Relation entity id, $entityId" }
            return EndpointRelationDefine(
                parts[0],
                decode(parts[1]),
                parts[2],
                decode(parts[3])
            )
        }

        data class EndpointIDDefinition(
            /**
             * Built by [ServiceID.buildId]
             */
            val serviceId: String? = null,
            val endpointName: String? = null
        )

        data class EndpointRelationDefine(
            /**
             * Built by [ServiceID.buildId]
             */
            val sourceServiceId: String? = null,
            val source: String? = null,

            /**
             * Built by [ServiceID.buildId]
             */
            val destServiceId: String? = null,
            val dest: String? = null
        )
    }

    /**
     * Process ID related functions.
     */
    object ProcessID {

        /**
         * @param instanceId built by [ServiceInstanceID.buildId]
         * @param name       process name
         * @return process id
         */
        fun buildId(instanceId: String?, name: String): String {
            var name = name
            if (name.isBlank()) {
                name = Const.BLANK_ENTITY_NAME
            }
            return Hashing.sha256().newHasher().putString(
                String.format(
                    Locale.ENGLISH,
                    "%s_%s",
                    name, instanceId
                ), Charsets.UTF_8
            ).hash().toString()
        }

        /**
         * @return encoded process relation id
         */
        fun buildRelationId(define: ProcessRelationDefine): String {
            return (define.sourceId + Const.RELATION_ID_CONNECTOR) + define.destId
        }

        /**
         * @return process relation ID object decoded from [.buildRelationId] result
         */
        fun analysisRelationId(entityId: String): ProcessRelationDefine {
            val parts: Array<String> =
                entityId.split(Const.RELATION_ID_PARSER_SPLIT.toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
            require(parts.size == 2) { "Illegal Process Relation entity id" }
            return ProcessRelationDefine(parts[0], parts[1])
        }

        data class ProcessRelationDefine(
            val sourceId: String? = null,
            val destId: String? = null
        )
    }

    /**
     * Network Address Alias ID related functions.
     */
    object NetworkAddressAliasDefine {

        /**
         * @return encoded network address id
         */
        fun buildId(name: String?): String {
            return encode(name)
        }

        /**
         * @return network address id object decoded from [.buildId] result
         */
        fun analysisId(id: String): String {
            return decode(id)
        }
    }
}
