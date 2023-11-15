import app.futured.kmptemplate.gradle.configuration.ProjectSettings
import app.futured.kmptemplate.gradle.ext.iosTargets

plugins {
    id(libs.plugins.com.android.library.get().pluginId)
    id(libs.plugins.kotlin.multiplatform.get().pluginId)
    id(libs.plugins.conventions.lint.get().pluginId)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = ProjectSettings.Kotlin.JvmTarget
            }
        }
    }

    iosTargets()

    sourceSets {
        commonMain {
            dependencies {
                // put your multiplatform dependencies here
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
    namespace = libs.versions.project.shared.persistence.namespace.get()
    compileSdk = ProjectSettings.Android.CompileSdkVersion
    defaultConfig {
        minSdk = ProjectSettings.Android.MinSdkVersion
    }
    compileOptions {
        sourceCompatibility = ProjectSettings.Android.JavaCompatibility
        targetCompatibility = ProjectSettings.Android.JavaCompatibility
    }
}
