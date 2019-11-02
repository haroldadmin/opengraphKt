version = ProjectProperties.versionName

plugins {
    `maven`
}

buildscript {
    repositories {
        jcenter()
        mavenCentral()
    }

    dependencies {
        classpath(kotlin("gradle-plugin", version = ProjectProperties.kotlinVersion))
    }
}
