import app.futured.kmptemplate.gradle.configuration.ProjectSettings
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.serialization)

    id(libs.plugins.conventions.lint.get().pluginId)
    id(libs.plugins.conventions.annotationProcessing.get().pluginId)
}

annotations {
    useKoin = true
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
            kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")

            dependencies {
                implementation(libs.koin.core)
                implementation(libs.koin.annotations)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.logging.kermit)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.androidx.datastore.preferences.core)
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
