package com.haroldadmin.opengraphKt

import java.io.File
import java.net.URL

object OpenGraphKt {
    fun clearCache() {
        Parser.clearCache()
    }
}

suspend fun URL.getOpenGraphTags(): Tags {
    return Parser.parse(this)
}

suspend fun File.getOpenGraphTags(): Tags {
    val text = bufferedReader().use { readText() }
    return Parser.parse(text)
}