package com.haroldadmin.opengraphKt

import java.io.File
import java.net.URL

/**
 * A singleton class to configure the Parser
 */
object OpenGraphKt {

    /**
     * Clears the cached Open-Graph tags
     */
    fun clearCache() {
        Parser.clearCache()
    }
}

/**
 * Parses the HTML at the given URL and returns the parsed Open-Graph tags
 *
 * The parsed tags are cached in memory, so that repeated requests can be fulfilled quickly.
 * Fetching the URL is done on [kotlinx.coroutines.Dispatchers.IO], while the parsing
 * is performed on [kotlinx.coroutines.Dispatchers.Default]
 */
suspend fun URL.getOpenGraphTags(): Tags {
    return Parser.parse(this)
}

/**
 * Reads the text from the given file, and returns the parsed Open-Graph tags
 *
 * The parsed tags are **NOT** cached in memory. This method exists mostly for testing
 * purposes. We do not delegate to [org.jsoup.Jsoup.parse] method for Files because
 * it throws a [java.net.MalformedURLException].
 */
suspend fun File.getOpenGraphTags(): Tags {
    return Parser.parse(this)
}
