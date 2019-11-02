version = ProjectProperties.versionName

buildscript {
    repositories {
        jcenter()
        mavenCentral()
    }

    dependencies {
        classpath(kotlin("gradle-plugin", version = ProjectProperties.kotlinVersion))
    }
}
