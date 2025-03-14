import app.futured.kmptemplate.gradle.configuration.ProjectSettings

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.moko.resources)

    id(libs.plugins.conventions.lint.get().pluginId)
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
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
                api(libs.moko.resources)
                implementation(libs.jetbrains.compose.runtime)
                implementation(libs.kotlinx.dateTime)

                implementation(projects.shared.platform)
            }
        }
        androidMain {
            dependencies {
                implementation(libs.androidx.compose.foundation)
            }
        }
    }
}

android {
    namespace = libs.versions.project.shared.resources.namespace.get()
    compileSdk = ProjectSettings.Android.CompileSdkVersion
    defaultConfig {
        minSdk = ProjectSettings.Android.MinSdkVersion
    }
    compileOptions {
        sourceCompatibility = ProjectSettings.Android.JavaCompatibility
        targetCompatibility = ProjectSettings.Android.JavaCompatibility
    }
    buildFeatures {
        compose = true
    }
}

multiplatformResources {
    resourcesPackage.set(libs.versions.project.shared.resources.packageName.get())
    resourcesClassName.set("MR")
    iosBaseLocalizationRegion.set(ProjectSettings.IOS.MokoBaseLocalizationRegion)
}
