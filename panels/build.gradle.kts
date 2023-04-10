@file:Suppress("UnstableApiUsage")

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.android.library")
    id("maven-publish")
    id("signing")
}

kotlin {
    jvm()
    android {
        publishLibraryVariants("release")
    }

    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
            }
        }
    }

    jvmToolchain(8)
    explicitApi()
}

android {
    namespace = "io.github.materiiapps.panels"
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

task<Jar>("emptyJavadocJar") {
    archiveClassifier.set("javadoc")
}

publishing {
    val sonatypeUsername: String? = System.getenv("SONATYPE_USERNAME")
    val sonatypePassword: String? = System.getenv("SONATYPE_PASSWORD")

    repositories {
        if (sonatypeUsername == null || sonatypePassword == null)
            mavenLocal()
        else {
            signing {
                useInMemoryPgpKeys(
                    System.getenv("SIGNING_KEY_ID"),
                    System.getenv("SIGNING_KEY"),
                    System.getenv("SIGNING_PASSWORD"),
                )
                sign(publications)
            }

            maven {
                credentials {
                    username = sonatypeUsername
                    password = sonatypePassword
                }
                setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            }
        }
    }

    publications.withType<MavenPublication> {
        artifact(tasks["emptyJavadocJar"])

        pom {
            name.set("panels")
            description.set("Discord-like side panels for Jetpack Compose Multiplatform.")
            url.set("https://github.com/MateriiApps/panels")
            licenses {
                license {
                    name.set("The Apache License, Version 2.0")
                    url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                }
            }
            developers {
                developer {
                    id.set("xinto")
                    name.set("Xinto")
                    url.set("https://github.com/X1nto/")
                }
                developer {
                    id.set("rushii")
                    name.set("rushii")
                    url.set("https://github.com/rushiiMachine/")
                }
            }
            scm {
                url.set("https://github.com/MateriiApps/panels")
                connection.set("scm:git:github.com/MateriiApps/panels.git")
                developerConnection.set("scm:git:ssh://github.com/MateriiApps/panels.git")
            }
        }
    }
}

// See https://youtrack.jetbrains.com/issue/KT-46466/Kotlin-MPP-publishing-Gradle-7-disables-optimizations-because-of-task-dependencies#focus=Comments-27-7102038.0-0
val dependsOnTasks = mutableListOf<String>()
tasks.withType<AbstractPublishToMaven>().configureEach {
    dependsOnTasks.add(name.replace("publish", "sign").replaceAfter("Publication", ""))
    dependsOn(dependsOnTasks)
}
