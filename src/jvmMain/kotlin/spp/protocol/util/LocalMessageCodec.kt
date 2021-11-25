package spp.protocol.util

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
