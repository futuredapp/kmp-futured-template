import app.futured.kmptemplate.gradle.configuration.ProjectSettings
import app.futured.kmptemplate.gradle.ext.iosTargets

plugins {
    id(libs.plugins.kotlin.multiplatform.get().pluginId)
}

kotlin {
    jvmToolchain(ProjectSettings.Kotlin.JvmToolchainVersion)

    applyDefaultHierarchyTemplate()

    jvm()

    iosTargets()

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.shared.arkitektDecompose.annotation)
            }
        }

        jvmMain {
            dependencies {
                implementation(libs.ksp.lib)
                implementation(libs.poet.interop)
            }
            kotlin.srcDir("src/main/kotlin")
            resources.srcDir("src/main/resources")
        }
    }
}
