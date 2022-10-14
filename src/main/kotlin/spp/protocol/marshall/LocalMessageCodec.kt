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
package spp.protocol.marshall

import io.vertx.core.buffer.Buffer
import io.vertx.core.eventbus.MessageCodec
import java.util.*

/**
 * Used to transmit protocol messages.
 *
 * @since 0.1.0
 */
class LocalMessageCodec<T> : MessageCodec<T, T> {
    override fun encodeToWire(buffer: Buffer, o: T): Unit =
        throw UnsupportedOperationException("Not supported yet.")

    override fun decodeFromWire(pos: Int, buffer: Buffer): T =
        throw UnsupportedOperationException("Not supported yet.")

    override fun transform(o: T): T = o
    override fun name(): String = UUID.randomUUID().toString()
    override fun systemCodecID(): Byte = -1
}
