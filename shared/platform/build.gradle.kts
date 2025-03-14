import app.futured.kmptemplate.gradle.configuration.ProjectSettings

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)

    id(libs.plugins.conventions.lint.get().pluginId)
}

kotlin {
    jvmToolchain(ProjectSettings.Kotlin.JvmToolchainVersion)

    androidTarget {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.koin.core)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}

android {
    namespace = libs.versions.project.shared.platform.namespace.get()
    compileSdk = ProjectSettings.Android.CompileSdkVersion
    defaultConfig {
        minSdk = ProjectSettings.Android.MinSdkVersion
    }
    compileOptions {
        sourceCompatibility = ProjectSettings.Android.JavaCompatibility
        targetCompatibility = ProjectSettings.Android.JavaCompatibility
    }
}
