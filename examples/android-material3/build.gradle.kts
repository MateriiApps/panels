plugins {
    id("org.jetbrains.compose")
    id("com.android.application")
    kotlin("android")
}

kotlin {
    jvmToolchain(11)
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "io.github.materiiapps.panels.example"
        minSdk = 21
        targetSdk = 33
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(project(":panels"))
    implementation(project(":panels-material3"))
    implementation(project(":examples:shared"))
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("androidx.compose.foundation:foundation:1.3.1")
    implementation("androidx.compose.material3:material3:1.0.1")
}
