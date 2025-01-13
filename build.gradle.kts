buildscript {
    dependencies {
        classpath(Hilt.hiltAndroidGradlePlugin)
        classpath(Kotlin.kotlinSerialization)
        classpath(Kotlin.jetbrainsKotlinPlugin)
        classpath(Kotlin.buildToolsGradle)
    }
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
plugins {
    id (Plugins.androidApplication).version(Plugins.androidPluginVersion).apply(false)
    id (Plugins.androidLibrary).version(Plugins.androidPluginVersion).apply(false)
    id (Plugins.jetbrainsKotlinAndroid).version(Plugins.jetbrainsPluginVersion).apply(false)
    id (Plugins.kotlinPluginSerialization).version(Plugins.jetbrainsPluginVersion).apply(false)
    id (Plugins.daggerHiltAndroid).version(Hilt.hiltVersion).apply(false)
}
