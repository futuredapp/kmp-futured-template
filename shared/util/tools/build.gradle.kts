import app.futured.kmptemplate.gradle.configuration.ProjectSettings
import app.futured.kmptemplate.gradle.ext.iosTargets

plugins {
    id(libs.plugins.com.android.library.get().pluginId)
    id(libs.plugins.kotlin.multiplatform.get().pluginId)
    id(libs.plugins.conventions.lint.get().pluginId)
}

kotlin {
    jvmToolchain(ProjectSettings.Kotlin.JvmToolchainVersion)

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = ProjectSettings.Android.KotlinJvmTarget
            }
        }
    }

    iosTargets()

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.decompose)
                implementation(libs.koin.core)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.logging.kermit)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.testCommon)
                implementation(libs.kotlin.testAnnotationsCommon)
            }
        }
    }
}

android {
    namespace = libs.versions.project.shared.util.namespace.get()
    compileSdk = ProjectSettings.Android.CompileSdkVersion
    defaultConfig {
        minSdk = ProjectSettings.Android.MinSdkVersion
    }
    compileOptions {
        sourceCompatibility = ProjectSettings.Android.JavaCompatibility
        targetCompatibility = ProjectSettings.Android.JavaCompatibility
    }
}
