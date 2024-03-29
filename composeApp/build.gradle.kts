import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.serialization)
    alias(libs.plugins.ksp)
}

kotlin {
    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        desktopMain.resources
            .srcDirs("build/generated/ksp/main/kotlin")

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)
            implementation(libs.serialization)
            implementation(libs.koin.core)
            implementation(libs.koin.annotations)
        }

        desktopMain.dependencies {
            implementation("org.brainflow:brainflow:5.12.0")
            implementation("xyz.avalonxr:oscsdk-kotlin:1.0-SNAPSHOT")
            implementation("ch.qos.logback:logback-classic:1.4.14")
            implementation(compose.desktop.currentOs)
        }

        configurations["ksp"].dependencies
            .add(project.dependencies.create("io.insert-koin:koin-ksp-compiler:1.3.1"))
    }
}

compose.desktop {
    application {
        mainClass = "xyz.brainforce.brainforce.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "xyz.brainforce.brainforce"
            packageVersion = "1.0.0"
        }
    }
}
