import app.futured.kmptemplate.gradle.configuration.ProjectSettings

plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    jvmToolchain(ProjectSettings.Kotlin.JvmToolchainVersion)

    applyDefaultHierarchyTemplate()

    jvm()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain {
            dependencies { }
        }
    }
}
