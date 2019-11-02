# OpenGraphKt

A dead simple [OpenGraph Tags](https://ogp.me/) parser for Kotlin, built using Coroutines and [Jsoup](https://jsoup.org/).

## Usage

The API is exposed through suspending extension functions.

```kotlin
// In a coroutine scope, or inside runBlocking

val url = URL("https://ogp.me/")
val tags = url.getOpenGraphTags()

println("""
Title: ${tags.title}
Description: ${tags.description}
Locale: ${tags.getProperty("locale")}
""")
```

The `getOpenGraphTags()` method returns a `Tags` class which only acts
as a wrapper around the underlying `Map<String, List<String>>` object.

`Tags` is an **inline class**, and [Inline classes](https://kotlinlang.org/docs/reference/inline-classes.html) are currently experimental, so make sure you're okay with that before using this library.

## Limitations

The library currently only supports the `og:` namespace.

## Contribution

Please feel free to open issues or contribute new features through pull requests.

## Download

*Instructions to be added soon...*