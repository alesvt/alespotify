import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    kotlin("plugin.serialization") version "2.1.10"
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }



    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Alespotify"
            isStatic = true
        }
    }

    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            // yo
            implementation(libs.ktor.client.android)
            implementation(libs.androidx.ui)
            implementation(libs.material)
            implementation(libs.androidx.ui.tooling.preview)

            implementation("androidx.compose.ui:ui-graphics:1.7.8")



        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

            // añadidas por mi
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.cio)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.kotlinx.serialization.json)
            implementation("io.realm.kotlin:library-base:1.16.0")
            // If using Device Sync
            implementation("io.realm.kotlin:library-sync:1.16.0")
            // If using coroutines with the SDK
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.bson.kotlinx)
            // implementation(libs.coil3.compose)
             // implementation("")
            implementation(libs.coil.compose)
            implementation(libs.coil)
            implementation(libs.coil.network.ktor)
            // Kotlin coroutine dependency
            implementation(libs.navigation.compose)

            implementation(compose.components.resources)
            implementation(libs.ui.util)
            implementation(libs.ktor.client.logging)

            implementation(compose.materialIconsExtended)



        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation("io.ktor:ktor-client-apache5:3.1.0")
            implementation(libs.kotlinx.coroutines.swing)

        }
        iosMain.dependencies {
            implementation("io.ktor:ktor-client-darwin:3.0.0")
            implementation(libs.ktor.client.ios)
        }
    }
}

android {
    namespace = "com.alespotify"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.alespotify"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.runtime.android)
    implementation(libs.androidx.foundation.android)
    implementation(libs.androidx.ui.graphics.android)
    implementation(libs.androidx.ui.android)
    implementation(libs.androidx.material3.android)
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "com.alespotify.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb, TargetFormat.Exe)
            packageName = "Alespotify"
            packageVersion = "1.0.0"
            description = "Aplicación para el módulo de proyecto final de Desarrollo de Aplicaciones Multiplataforma"
            copyright = "© 2025 alesvolta. Todos los derechos reservados"
            windows {
                iconFile.set(project.file("/logo/Windows/applogo.ico"))
                menuGroup = "Alespotify"
                shortcut
                vendor = "Alesvolta"

            }
        }

    }
}
