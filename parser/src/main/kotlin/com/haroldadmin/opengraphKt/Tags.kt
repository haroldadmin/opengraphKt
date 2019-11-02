package com.haroldadmin.opengraphKt

/**
 * An inline class wrapping a Map of Open Graph tags.
 *
 * The keys in the map contain the value of the 'property' attribute from <meta> tags, and the values for each key
 * are all the <meta> tags containing this exact 'property'.
 */
inline class Tags(private val tags: Map<String, List<String>>) {

    /**
     * Get the first "title" og-tag from the web page, if any
     */
    val title: String?
        get() = allTitles().firstOrNull()

    /**
     * Get the first image url found in "og:image", "og:image:url" or "og:image:secure_url" on the web page, if any
     */
    val image: String?
        get() = allImages().firstOrNull()

    /**
     * Get the first url found in "og:url" on the web page
     */
    val url: String?
        get() = allUrls().firstOrNull()

    /**
     * Get the first "og:description" on the webpage, if any
     */
    val description: String?
        get() = allDescriptions().firstOrNull()

    /**
     * Get all "og:title" values on the web page.
     */
    fun allTitles(): List<String> {
        return tags["title"] ?: emptyList()
    }

    /**
     * Get all the "og:image", "og:image:url" and "og:image:secure_url" tags on the web page
     */
    fun allImages(): List<String> {
        return extractImageUrl()
    }

    /**
     * Get all the "og:description" tags on the web page
     */
    fun allDescriptions(): List<String> {
        return tags["description"] ?: emptyList()
    }

    /**
     * Get all the "og:url" tags on the web page
     */
    fun allUrls(): List<String> {
        return tags["url"] ?: emptyList()
    }

    /**
     * Get the first occurrence of any arbitrary open-graph tag from the web page if it exists
     *
     * Example:
     * ```kotlin
     * val altText = tags.getProperty("image:alt")
     * ```
     */
    fun getProperty(name: String): String? {
        return getProperties(name).firstOrNull()
    }


    /**
     * Get all occurrences of any abitrary open-graph tag from the web page, if any exists
     *
     * Example:
     * ```kotlin
     * val locales = tags.getProperties("locale")
     * ```
     */
    fun getProperties(name: String): List<String> {
        return tags[name] ?: emptyList()
    }

    private fun extractImageUrl(): List<String> {
        return tags["image"] ?: tags["image:url"] ?: tags["image:secure_url"] ?: emptyList()
    }
}

