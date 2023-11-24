import app.futured.kmptemplate.gradle.configuration.ProjectSettings
import app.futured.kmptemplate.gradle.ext.iosTargets

plugins {
    id(libs.plugins.com.android.library.get().pluginId)
    id(libs.plugins.kotlin.multiplatform.get().pluginId)
    id(libs.plugins.conventions.lint.get().pluginId)

    alias(libs.plugins.moko.resources)
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
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
                api(libs.moko.resources)
            }
        }
        androidMain {
            // Workaround for: https://github.com/icerockdev/moko-resources/issues/531
            dependsOn(commonMain.get())
            dependencies {
                implementation(libs.androidx.compose.runtime)
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
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
}

multiplatformResources {
    multiplatformResourcesPackage = libs.versions.project.shared.resources.packageName.get()
    multiplatformResourcesClassName = "MR"
    iosBaseLocalizationRegion = ProjectSettings.IOS.MokoBaseLocalizationRegion
}
