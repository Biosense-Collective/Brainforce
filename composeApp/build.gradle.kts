import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
//    alias(libs.plugins.serialization)
    kotlin("plugin.serialization") version "1.9.21"
    alias(libs.plugins.ksp)
}

kotlin {
    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)
            implementation(libs.serialization)
        }

        desktopMain.resources
            .srcDirs("build/generated/ksp/main/kotlin")

        desktopMain.dependencies {
            implementation("org.brainflow:brainflow:5.12.0")
            implementation("xyz.avalonxr:oscsdk-kotlin:1.0-SNAPSHOT")
            implementation("ch.qos.logback:logback-classic:1.4.14")
            implementation(compose.desktop.currentOs)
            implementation(libs.koin.core)
            implementation(libs.koin.annotations)
            configurations["ksp"].dependencies
                .add(project.dependencies.create("io.insert-koin:koin-ksp-compiler:1.3.1"))
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "xyz.brainforce.brainforce"
            packageVersion = "1.0.0"
        }
    }
}
