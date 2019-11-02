package com.haroldadmin.opengraphkt.sample

import com.haroldadmin.opengraphKt.getOpenGraphTags
import kotlinx.coroutines.runBlocking
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

object Application {
    fun run() = runBlocking {
        println("Enter URL: ")
        try {
            val url = URL(readLine())
            println(url.getOpenGraphTags())
        } catch (ex: MalformedURLException) {
            println("Invalid URL: ${ex.localizedMessage}")
        } catch (ex: IOException) {
            println("Something went wrong.")
            ex.printStackTrace()
        }
    }
}

fun main() {
    Application.run()
}