@file:Suppress("UnstableApiUsage")

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.android.library")
    id("maven-publish")
    id("signing")
}

kotlin {
    jvm("desktop")
    android("android") {
        publishLibraryVariants("release")
    }

    sourceSets {
        named("commonMain") {
            dependencies {
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.material3)
                implementation(project(":panels"))
            }
        }
    }

    jvmToolchain(8)
}

android {
    namespace = "io.github.materiiapps.panels.material3"
    compileSdk = 33

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    defaultConfig {
        minSdk = 21
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        buildConfig = false
        resValues = false
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}
