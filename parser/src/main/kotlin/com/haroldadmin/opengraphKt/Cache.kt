package com.haroldadmin.opengraphKt

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * A synchronized implementation of a HashMap based Cache.
 *
 * @param T the type of the key used in the map
 * @param U the type of the value used in the map
 *
 *  Reads and writes to the cache among different coroutines are synchronized
 *  with a [Mutex] to prevent race conditions.
 */
internal class Cache<T, U> {
    private val map = mutableMapOf<T, U>()
    private val mutex = Mutex()

    suspend fun get(key: T): U? = mutex.withLock {
        return map[key]
    }

    suspend fun set(key: T, value: U) = mutex.withLock {
        map[key] = value
    }

    fun clear() {
        map.clear()
    }

    fun isEmpty(): Boolean {
        return map.isEmpty()
    }

    fun isNotEmpty(): Boolean = !isEmpty()
}

/**
 * Returns the value mapped to the key if present, otherwise computes it, stores it and returns it.
 *
 * @param key The key to lookup
 * @param valueProducer The method which computes the value and returns it
 */
internal suspend fun <T, U> Cache<T, U>.computeIfAbsent(key: T, valueProducer: suspend Cache<T, U>.(T) -> U): U {
    return get(key) ?: run {
        val value = valueProducer(key)
        set(key, value)
        value
    }
}