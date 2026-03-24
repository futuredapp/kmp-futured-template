import app.futured.kmptemplate.gradle.configuration.ProjectSettings

plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    jvmToolchain(ProjectSettings.Kotlin.JvmToolchainVersion)

    applyDefaultHierarchyTemplate()

    jvm()

    iosArm64()
    iosSimulatorArm64()
}
