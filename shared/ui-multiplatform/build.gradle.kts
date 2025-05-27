import app.futured.kmptemplate.gradle.configuration.ProjectSettings
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    id(libs.plugins.conventions.lint.get().pluginId)
}

kotlin {
    jvmToolchain(ProjectSettings.Kotlin.JvmToolchainVersion)

    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.fromTarget(ProjectSettings.Android.KotlinJvmTargetNum))
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {


        commonMain {
            dependencies {
                implementation(libs.koin.core)
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation(compose.components.resources)
                implementation(libs.androidx.lifecycle.compose)
                implementation(projects.shared.feature)
                implementation(projects.shared.resources)
                implementation(projects.shared.arkitektDecompose)
                implementation(libs.moko.resources.multiplatform)
                implementation(libs.decompose.compose.ext)
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
    namespace = libs.versions.project.shared.ui.multiplatform.namespace.get()
    compileSdk = ProjectSettings.Android.CompileSdkVersion
    defaultConfig {
        minSdk = ProjectSettings.Android.MinSdkVersion
    }
    compileOptions {
        sourceCompatibility = ProjectSettings.Android.JavaCompatibility
        targetCompatibility = ProjectSettings.Android.JavaCompatibility
    }
}
dependencies {
    debugImplementation(libs.androidx.compose.ui.tooling)
}
