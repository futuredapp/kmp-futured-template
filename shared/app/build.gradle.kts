plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.kotlin.multiplatform)
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

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = projectSettings.baseName.get()
            binaryOptions += "bundleId" to projectSettings.bundleId.get()

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
                implementation(kotlin("test"))
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
    namespace = projectSettings.shared.app.namespace.get()
    compileSdk = projectSettings.compileSdk.get().toInt()
    defaultConfig {
        minSdk = projectSettings.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
