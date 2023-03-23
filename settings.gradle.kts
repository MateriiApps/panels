pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }

    plugins {
        kotlin("multiplatform").version(extra["kotlin.version"] as String) apply false
        kotlin("android").version(extra["kotlin.version"] as String) apply false
        id("com.android.application").version(extra["agp.version"] as String) apply false
        id("com.android.library").version(extra["agp.version"] as String) apply false
        id("org.jetbrains.compose").version(extra["compose.version"] as String) apply false
    }
}

include(
    ":examples:android", ":examples:android-material3",
    ":examples:desktop", ":examples:desktop-material3")
include(":panels", ":panels-material3")
