plugins {
    kotlin("multiplatform") apply false
    kotlin("android") apply false
    id("com.android.application") apply false version "7.4.2"
    id("com.android.library") apply false version "7.4.2"
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

project("panels").afterEvaluate {
    val publishing = extensions.getByType<PublishingExtension>()
    val signing = extensions.getByType<SigningExtension>()

    task<Jar>("emptyJavadocJar") {
        archiveClassifier.set("javadoc")
    }

    publishing.apply {
        repositories {
            val sonatypeUsername = System.getenv("SONATYPE_USERNAME")
            val sonatypePassword = System.getenv("SONATYPE_PASSWORD")

            if (sonatypeUsername == null || sonatypePassword == null)
                mavenLocal()
            else {
                signing.apply {
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