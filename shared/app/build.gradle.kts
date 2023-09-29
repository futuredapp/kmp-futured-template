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

            linkerOpts += "-lsqlite3"

            export(libs.decompose)
            export(libs.essenty)
            export(projects.shared.feature)
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.shared.feature)
                implementation(projects.shared.network)

                // Decompose
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
                api(projects.shared.feature)
                api(libs.decompose)
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
