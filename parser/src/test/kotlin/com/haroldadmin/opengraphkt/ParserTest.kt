package com.haroldadmin.opengraphkt

import com.haroldadmin.opengraphKt.Parser
import com.haroldadmin.opengraphKt.getOpenGraphTags
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test
import java.io.File

internal class ParserTest {

    @Test
    fun `load from file test`() = runBlocking {
        val file = file("/OpenGraphWebsiteSource.html")
        with(file.getOpenGraphTags()) {
            assert(title == "Open Graph protocol")
            assert(url == "http://ogp.me/")
            assert(image == "http://ogp.me/logo.png")
            assert(description == "The Open Graph protocol enables any web page to become a rich object in a social graph.")
        }
    }

    @Test
    fun `load from URL test`() = runBlocking {
        MockWebServer().apply {
            enqueue(MockResponse().setBody(file("/OpenGraphWebsiteSource.html").readText()))
        }.use { server ->
            val fileUrl = server.url("/ogp").toUrl()
            with(fileUrl.getOpenGraphTags()) {
                val tags = this
                assert(title == "Open Graph protocol")
                assert(url == "http://ogp.me/")
                assert(image == "http://ogp.me/logo.png")
                assert(description == "The Open Graph protocol enables any web page to become a rich object in a social graph.")
            }
        }
    }

    @Test
    fun `arbitrary property test`() = runBlocking {
        val file = file("/OpenGraphWebsiteSource.html")
        with(file.getOpenGraphTags()) {
            assert(getProperty("image:width") == "300")
            assert(getProperty("image:height") == "300")
            assert(getProperty("image:alt") == "The Open Graph logo")
            assert(getProperty("image:type") == "image/png")
        }
    }

    @Test
    fun `parsing of multiple tags of the same type test`() = runBlocking {
        val file = file("/OpenGraphWebsiteSource.html")
        with(file.getOpenGraphTags()) {
            assert(allImages().size == 2)
            assert(allImages() == listOf("http://ogp.me/logo.png", "http://ogp.me/logo2.png"))
        }
    }

    @Test
    fun `should not parse meta tags outside of head tag test`() = runBlocking {
        val file = file("/OpenGraphWebsiteSource.html")
        with(file.getOpenGraphTags()) {
            assert("The Rock" !in allTitles())
        }
    }

    @Test
    fun `clearing cache should empty cache`() = runBlocking {
        MockWebServer().apply {
            enqueue(MockResponse().setBody(file("/OpenGraphWebsiteSource.html").readText()))
        }.use { server ->
            val fileUrl = server.url("/ogp").toUrl()
            fileUrl.getOpenGraphTags()
            assert(Parser.cache.isNotEmpty())
            Parser.clearCache()
            assert(Parser.cache.isEmpty())
        }
    }

    @Test
    fun `request to the same url again should be resolved using the cache`() = runBlocking {
        MockWebServer().apply {
            enqueue(MockResponse().setBody(file("/OpenGraphWebsiteSource.html").readText()))
        }.use { server ->
            val fileUrl = server.url("/ogp").toUrl()
            repeat(2) {
                fileUrl.getOpenGraphTags()
            }
            assert(server.requestCount == 1)
       }
    }
}

// Relative path to the current project
private fun Any.file(path: String): File {
    return File(this::class.java.getResource(path).toURI())
}