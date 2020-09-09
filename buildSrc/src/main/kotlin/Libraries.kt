object Libraries {

    const val jsoup = "org.jsoup:jsoup:1.13.1"
    const val mockWebServer = "com.squareup.okhttp3:mockwebserver:4.8.1"

    object Kotlin {
        const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${ProjectProperties.kotlinVersion}"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9"
        const val test = "org.jetbrains.kotlin:kotlin-test:${ProjectProperties.kotlinVersion}"
        const val testJunit = "org.jetbrains.kotlin:kotlin-test-junit:${ProjectProperties.kotlinVersion}"
    }
}