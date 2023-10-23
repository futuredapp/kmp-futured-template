plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.parcelize)
}

private val projectSettings = libs.versions.project

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = projectSettings.jvmTarget.get()
            }
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.decompose)
                implementation(libs.koin.core)
                implementation(libs.kotlinx.immutableCollections)

                implementation(projects.shared.network)
                implementation(projects.shared.persistance)
                implementation(projects.shared.util)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.testCommon)
                implementation(libs.kotlin.testAnnotationsCommon)
            }
        }
    }
}

android {
    namespace = projectSettings.shared.feature.namespace.get()
    compileSdk = projectSettings.compileSdk.get().toInt()
    defaultConfig {
        minSdk = projectSettings.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
