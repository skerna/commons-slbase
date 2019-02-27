package io.skerna.lbase


expect inline fun <R> synchronized(lock: Any, block: () -> R): R