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
package spp.protocol.marshall

import io.vertx.serviceproxy.ServiceException
import spp.protocol.platform.auth.RolePermission
import spp.protocol.service.error.InstrumentAccessDenied
import spp.protocol.service.error.LiveInstrumentException
import spp.protocol.service.error.PermissionAccessDenied

object ServiceExceptionConverter {

    fun fromEventBusException(exception: String, toEventBusException: Boolean = false): ServiceException {
        return if (exception.startsWith("EventBusException")) {
            var exceptionType = exception.substringAfter("EventBusException:")
            exceptionType = exceptionType.substringBefore("[")
            var exceptionParams = exception.substringAfter("[")
            exceptionParams = exceptionParams.substringBefore("]")
            val exceptionMessage = exception.substringAfter("]: ").trim { it <= ' ' }
            if (LiveInstrumentException::class.java.simpleName == exceptionType) {
                LiveInstrumentException(
                    LiveInstrumentException.ErrorType.valueOf(exceptionParams),
                    exceptionMessage
                ).let { if (toEventBusException) it.toEventBusException() else it }
            } else if (InstrumentAccessDenied::class.java.simpleName == exceptionType) {
                InstrumentAccessDenied(
                    exceptionParams,
                    exceptionMessage
                ).let { if (toEventBusException) it.toEventBusException() else it }
            } else if (PermissionAccessDenied::class.java.simpleName == exceptionType) {
                PermissionAccessDenied(
                    RolePermission.valueOf(exceptionParams),
                    exceptionMessage
                ).toEventBusException()
            } else {
                throw UnsupportedOperationException(exceptionType)
            }
        } else {
            throw IllegalArgumentException(exception)
        }
    }
}
