package com.haroldadmin.opengraphkt

import com.haroldadmin.opengraphKt.Cache
import com.haroldadmin.opengraphKt.computeIfAbsent
import kotlinx.coroutines.*
import org.junit.Test
import kotlin.test.assertEquals

internal class CacheTest {

    @Test
    fun concurrentWritesTest() = runBlocking {
        val cache = Cache<String, Int>()
        val key = "url"
        val testIterations = 1000

        runBlocking {
            repeat(testIterations) { iteration ->
                launch {
                    cache.set(key, iteration)
                }
            }
        }

        assertEquals(testIterations - 1, cache.get(key))
    }

    @Test
    fun computeIfAbsentTest() = runBlocking {
        val cache = Cache<Int, String>()
        var value = cache.get(42)

        assert(value == null)

        cache.computeIfAbsent(42) { key ->
            key.toString()
        }

        assert(cache.get(42) == "42")
    }

    @Test
    fun clearCacheTest() = runBlocking {
        val cache = Cache<Int, String>()
        cache.set(42, "42")
        assert(cache.isNotEmpty())
        cache.clear()
        assert(cache.get(42) == null)
        assert(cache.isEmpty())
    }

}