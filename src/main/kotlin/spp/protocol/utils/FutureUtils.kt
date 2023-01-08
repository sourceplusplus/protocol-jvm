package spp.protocol.utils

import io.vertx.core.Future

fun <T> Future<T>.toCompletableFuture(): java.util.concurrent.CompletableFuture<T> {
    val future = java.util.concurrent.CompletableFuture<T>()
    onComplete {
        if (it.succeeded()) {
            future.complete(it.result())
        } else {
            future.completeExceptionally(it.cause())
        }
    }
    return future
}
fun <T> Future<T>.applyToCompletableFuture(completableFuture: java.util.concurrent.CompletableFuture<T>): java.util.concurrent.CompletableFuture<T> {
    onComplete {
        if (it.succeeded()) {
            completableFuture.complete(it.result())
        } else {
            completableFuture.completeExceptionally(it.cause())
        }
    }
    return completableFuture
}