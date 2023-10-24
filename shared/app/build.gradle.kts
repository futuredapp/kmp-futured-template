import app.futured.kmptemplate.gradle.configuration.ProjectSettings
import app.futured.kmptemplate.gradle.ext.iosTargets

plugins {
    id(libs.plugins.com.android.library.get().pluginId)
    id(libs.plugins.kotlin.multiplatform.get().pluginId)
    id(libs.plugins.conventions.lint.get().pluginId)
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = ProjectSettings.Kotlin.JvmTarget
            }
        }
    }

    iosTargets {
        it.binaries.framework {
            baseName = ProjectSettings.IOS.FrameworkName
            binaryOptions += "bundleId" to ProjectSettings.IOS.FrameworkBundleId

            // Enable if SQLite is used in project (such as Apollo cache, or SQLDelight)
            // linkerOpts += "-lsqlite3"

            export(projects.shared.platform)
            export(projects.shared.util)
            export(projects.shared.feature)

            export(libs.decompose)
            export(libs.essenty)
            export(libs.kotlinx.immutableCollections)
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.shared.platform)
                implementation(projects.shared.feature)
                implementation(projects.shared.network)

                implementation(libs.decompose)
                implementation(libs.koin.core)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.testCommon)
                implementation(libs.kotlin.testAnnotationsCommon)
            }
        }

        val iosMain by getting {
            dependencies {
                api(projects.shared.platform)
                api(projects.shared.util)
                api(projects.shared.feature)

                api(libs.decompose)
                api(libs.kotlinx.immutableCollections)
            }
        }
    }
}

android {
    namespace = libs.versions.project.shared.app.namespace.get()
    compileSdk = ProjectSettings.Android.CompileSdkVersion
    defaultConfig {
        minSdk = ProjectSettings.Android.MinSdkVersion
    }
    compileOptions {
        sourceCompatibility = ProjectSettings.Android.JavaCompatibility
        targetCompatibility = ProjectSettings.Android.JavaCompatibility
    }
}
