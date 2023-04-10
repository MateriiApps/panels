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

afterEvaluate {
    publishing {
        repositories {
            val sonatypeUsername = System.getenv("SONATYPE_USERNAME")
            val sonatypePassword = System.getenv("SONATYPE_PASSWORD")

            if (sonatypeUsername == null || sonatypePassword == null)
                mavenLocal()
            else {
                signing {
                    useInMemoryPgpKeys(
                        System.getenv("SIGNING_KEY_ID"),
                        System.getenv("SIGNING_KEY"),
                        System.getenv("SIGNING_PASSWORD"),
                    )
                    sign(publishing.publications)
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
}
