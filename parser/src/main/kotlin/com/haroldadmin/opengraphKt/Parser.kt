package com.haroldadmin.opengraphKt

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.io.File
import java.net.URL

internal object Parser {

    val cache: MutableMap<URL, Tags> = mutableMapOf()

    suspend fun parse(url: URL): Tags = withContext(Dispatchers.IO) {
        cache[url] ?: run {
            val document = Jsoup.connect(url.toString()).get()
            val tags = extractTags(document)
            cacheTags(url, tags)
            tags
        }
    }

    suspend fun parse(html: String): Tags = withContext(Dispatchers.IO) {
        val document = Jsoup.parse(html)
        extractTags(document)
    }

    suspend fun parse(file: File): Tags = withContext(Dispatchers.IO) {
        val html = file.readText()
        parse(html)
    }

    fun clearCache() {
        cache.clear()
    }

    private suspend fun extractTags(document: Document): Tags = withContext(Dispatchers.Default) {
        val openGraphTags = document
                .head()
                .getElementsByTag("meta")
                .filter { it.isOpenGraphTag() }
                .groupBy { it.propertyWithoutOgTag() }
                .mapValues { openGraphTag ->
                    openGraphTag.value.map { element -> element.attr("content") }
                }

        Tags(openGraphTags)
    }

    private fun cacheTags(url: URL, tags: Tags) = synchronized(cache) {
        cache[url] = tags
    }

    private fun Element.isOpenGraphTag(): Boolean {
        return hasAttr("property") && attr("property").startsWith("og:")
    }

    private fun Element.propertyWithoutOgTag(): String {
        return attr("property").substringAfter(":")
    }
}
