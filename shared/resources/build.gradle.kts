@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import app.futured.kmptemplate.gradle.configuration.ProjectSettings
import app.futured.kmptemplate.gradle.ext.iosTargets
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    id(libs.plugins.com.android.library.get().pluginId)
    id(libs.plugins.kotlin.multiplatform.get().pluginId)
    id(libs.plugins.conventions.lint.get().pluginId)

    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.moko.resources)
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
}

kotlin {
    jvmToolchain(ProjectSettings.Kotlin.JvmToolchainVersion)

    applyDefaultHierarchyTemplate()

    androidTarget {
        compilerOptions {
            jvmTarget.set(ProjectSettings.Android.KotlinJvmTarget)
        }
    }

    iosTargets()

    sourceSets {
        commonMain {
            dependencies {
                api(libs.moko.resources)
                implementation(libs.jetbrains.compose.runtime)
            }
        }
        androidMain {
            dependencies {
                implementation(libs.androidx.compose.foundation)
            }
        }
    }
}

android {
    namespace = libs.versions.project.shared.resources.namespace.get()
    compileSdk = ProjectSettings.Android.CompileSdkVersion
    defaultConfig {
        minSdk = ProjectSettings.Android.MinSdkVersion
    }
    compileOptions {
        sourceCompatibility = ProjectSettings.Android.JavaCompatibility
        targetCompatibility = ProjectSettings.Android.JavaCompatibility
    }
    buildFeatures {
        compose = true
    }
}

multiplatformResources {
    resourcesPackage.set(libs.versions.project.shared.resources.packageName.get())
    resourcesClassName.set("MR")
    iosBaseLocalizationRegion.set(ProjectSettings.IOS.MokoBaseLocalizationRegion)
}
