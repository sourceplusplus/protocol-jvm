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

import io.vertx.core.buffer.Buffer
import io.vertx.core.eventbus.MessageCodec
import spp.protocol.instrument.*
import java.util.*

/**
 * Used to transmit [LiveInstrument] messages.
 */
class LiveInstrumentCodec<T : LiveInstrument> : MessageCodec<T, T> {
    override fun encodeToWire(buffer: Buffer, o: T) {
        when (o) {
            is LiveBreakpoint -> buffer.appendString(o.toJson().toString())
            is LiveLog -> buffer.appendString(o.toJson().toString())
            is LiveMeter -> buffer.appendString(o.toJson().toString())
            is LiveSpan -> buffer.appendString(o.toJson().toString())
            else -> error("Unknown live instrument type: $o")
        }
    }

    override fun decodeFromWire(pos: Int, buffer: Buffer): T {
        val json = buffer.toJsonObject()
        return when (json.getString("type")) {
            LiveInstrumentType.BREAKPOINT.name -> LiveBreakpoint(json) as T
            LiveInstrumentType.LOG.name -> LiveLog(json) as T
            LiveInstrumentType.METER.name -> LiveMeter(json) as T
            LiveInstrumentType.SPAN.name -> LiveSpan(json) as T
            else -> error("Unknown live instrument type: $json")
        }
    }

    override fun transform(o: T): T = o
    override fun name(): String = UUID.randomUUID().toString()
    override fun systemCodecID(): Byte = -1
}
