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
    implementation("androidx.activity:activity-compose:1.7.0")
    implementation("androidx.compose.foundation:foundation:1.4.0")
    implementation("androidx.compose.material3:material3:1.1.0-beta01")
}
