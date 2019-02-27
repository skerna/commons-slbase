package io.skerna.lbase

actual inline fun <R> synchronized(lock: Any, block: () -> R): R {
    return block()
}