plugins {
    id("org.jetbrains.compose")
    id("com.android.application")
    kotlin("android")
}

kotlin {
    jvmToolchain(8)
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "io.github.materiiapps.panels.example"
        minSdk = 21
        targetSdk = 33
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(project(":panels"))
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("androidx.compose.foundation:foundation:1.3.1")
}
