import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-library`
    kotlin("jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(Libraries.Kotlin.stdLib)
    implementation(Libraries.Kotlin.coroutines)
    implementation(project(":parser"))
}

repositories {
    mavenCentral()
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
    freeCompilerArgs = listOf("-XXLanguage:+InlineClasses")
}