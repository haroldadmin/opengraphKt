package com.haroldadmin.opengraphKt

inline class Tags(private val tags: Map<String, List<String>>) {

    val title: String?
        get() = allTitles().firstOrNull()

    val image: String?
        get() = allImages().firstOrNull()

    val url: String?
        get() = allUrls().firstOrNull()

    val description: String?
        get() = allDescriptions().firstOrNull()

    fun allTitles(): List<String> {
        return tags["title"] ?: emptyList()
    }

    fun allImages(): List<String> {
        return extractImageUrl()
    }

    fun allDescriptions(): List<String> {
        return tags["description"] ?: emptyList()
    }

    fun allUrls(): List<String> {
        return tags["url"] ?: emptyList()
    }

    fun getProperty(name: String): String? {
        return getProperties(name).firstOrNull()
    }

    fun getProperties(name: String): List<String> {
        return tags[name] ?: emptyList()
    }

    private fun extractImageUrl(): List<String> {
        return tags["image"] ?: tags["image:url"] ?: tags["image:secure_url"] ?: emptyList()
    }
}

