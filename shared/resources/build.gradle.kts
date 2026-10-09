import app.futured.kmptemplate.gradle.configuration.ProjectSettings

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.moko.resources)

    id(libs.plugins.conventions.lint.get().pluginId)
}

kotlin {
    jvmToolchain(ProjectSettings.Kotlin.JvmToolchainVersion)

    // Turns off warnings about beta feature https://youtrack.jetbrains.com/issue/KT-61573
    compilerOptions.freeCompilerArgs.add("-Xexpect-actual-classes")

    android {
        namespace = libs.versions.project.shared.resources.namespace.get()
        compileSdk = ProjectSettings.Android.CompileSdkVersion
        minSdk = ProjectSettings.Android.MinSdkVersion
        androidResources { enable = true }
    }

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
                implementation(project.dependencies.platform(libs.androidx.compose.bom))
                implementation(libs.androidx.compose.foundation)
            }
        }
    }
}

multiplatformResources {
    resourcesPackage.set(libs.versions.project.shared.resources.packageName.get())
    resourcesClassName.set("MR")
    iosBaseLocalizationRegion.set(ProjectSettings.IOS.MokoBaseLocalizationRegion)
}
