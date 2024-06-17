import app.futured.kmptemplate.gradle.configuration.ProjectSettings

plugins {
    id(libs.plugins.kotlin.multiplatform.get().pluginId)
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
            dependencies {
                implementation(projects.shared.util.componentAnnotation)
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
