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
package spp.protocol.service.extend

import io.vertx.core.Handler
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.core.net.NetSocket
import io.vertx.ext.eventbus.bridge.tcp.impl.protocol.FrameHelper
import io.vertx.ext.eventbus.bridge.tcp.impl.protocol.FrameParser

class TCPServiceSocket(private val vertx: Vertx, private val socket: NetSocket) {

    private val exceptionHandlers = mutableListOf<Handler<Throwable>>()
    private val closeHandlers = mutableListOf<Handler<Void>>()

    init {
        socket.handler(FrameParser(TCPServiceFrameParser(vertx, socket)))

        //periodically send ping frame to keep connection alive
        val pingId = vertx.setPeriodic(5000) {
            FrameHelper.writeFrame(JsonObject().put("type", "ping"), socket)
        }
        exceptionHandler { vertx.cancelTimer(pingId) }
        closeHandler { vertx.cancelTimer(pingId) }
    }

    fun exceptionHandler(handler: Handler<Throwable>): TCPServiceSocket {
        exceptionHandlers.add(handler)
        socket.exceptionHandler {
            exceptionHandlers.forEach { handler ->
                handler.handle(it)
            }
        }
        return this
    }

    fun closeHandler(handler: Handler<Void>): TCPServiceSocket {
        closeHandlers.add(handler)
        socket.closeHandler {
            closeHandlers.forEach { handler ->
                handler.handle(it)
            }
        }
        return this
    }
}
