import app.futured.kmptemplate.gradle.configuration.ProjectSettings

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)

    id(libs.plugins.conventions.lint.get().pluginId)
}

kotlin {
    jvmToolchain(ProjectSettings.Kotlin.JvmToolchainVersion)
    compilerOptions.freeCompilerArgs.add("-Xexpect-actual-classes")

    android {
        namespace = libs.versions.project.shared.ui.namespace.get()
        compileSdk = ProjectSettings.Android.CompileSdkVersion
        minSdk = ProjectSettings.Android.MinSdkVersion
    }

    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.bundles.cmp)

                implementation(projects.shared.feature)
                implementation(projects.shared.arkitektDecompose)
                implementation(projects.shared.kmpResources)

                implementation(libs.moko.resources)
                implementation(libs.moko.resources.compose)

                implementation(libs.decompose)
                implementation(libs.decompose.compose.ext)
                implementation(libs.kotlinx.immutableCollections)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.androidx.lifecycle.compose)
            }
        }

        androidMain {
            dependencies {
                implementation(compose.uiTooling)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}
