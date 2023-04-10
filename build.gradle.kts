plugins {
    kotlin("multiplatform") apply false
    kotlin("android") apply false
    id("com.android.application") apply false
    id("com.android.library") apply false
    id("org.jetbrains.compose") apply false
}

subprojects {
    group = "io.github.materiiapps.panels"
    version = "1.0.0"

    repositories {
        repositories {
            google()
            mavenCentral()
        }
    }
}
