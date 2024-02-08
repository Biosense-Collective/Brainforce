rootProject.name = "Brainforce"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    val gprUser: String by settings
    val gprKey: String by settings

    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        // Brainflow
        maven {
            url = uri("https://maven.pkg.github.com/brainflow-dev/brainflow")
            credentials {
                username = gprUser
                password = gprKey
            }
        }
        maven {
            url = uri("https://maven.pkg.github.com/Biosense-Collective/OSCSDK-Kotlin")
            credentials {
                username = gprUser
                password = gprKey
            }
        }
    }
}

include(":composeApp")