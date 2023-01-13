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
package spp.protocol.service.listen

import io.vertx.core.Future
import io.vertx.core.Promise
import io.vertx.core.Vertx
import spp.protocol.instrument.LiveInstrument

/**
 * Listen to [LiveInstrument] events for the given [subscriptionId].
 *
 * @param subscriptionId the subscription id (can be developer id or instrument id)
 * @param listener the listener to be called when an instrument event is received
 */
fun Vertx.addLiveInstrumentListener(
    subscriptionId: String,
    listener: LiveInstrumentListener
): Future<LiveInstrumentListenerImpl> {
    val promise = Promise.promise<LiveInstrumentListenerImpl>()
    val instrumentListenerImpl = LiveInstrumentListenerImpl(this, subscriptionId, listener)
    instrumentListenerImpl.consumer.completionHandler {
        if (it.succeeded()) {
            promise.complete(instrumentListenerImpl)
        } else {
            promise.fail(it.cause())
        }
    }
    return promise.future()
}
