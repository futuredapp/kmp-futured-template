import app.futured.kmptemplate.gradle.configuration.ProjectSettings
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.koin)

    id(libs.plugins.conventions.lint.get().pluginId)
}

dependencies {
    add("kspCommonMainMetadata", libs.futured.arkitekt.decomposeProcessor)
}

tasks.withType<KotlinCompilationTask<*>>().configureEach {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}

kotlin {
    jvmToolchain(ProjectSettings.Kotlin.JvmToolchainVersion)

    compilerOptions {
        // Turns off warnings about beta feature https://youtrack.jetbrains.com/issue/KT-61573
        freeCompilerArgs.add("-Xexpect-actual-classes")
        // Arkitekt UseCases support (will become stable in Kotlin 2.4.0)
        freeCompilerArgs.add("-Xcontext-parameters")
    }

    android {
        namespace = libs.versions.project.shared.feature.namespace.get()
        compileSdk = ProjectSettings.Android.CompileSdkVersion
        minSdk = ProjectSettings.Android.MinSdkVersion

        withHostTest {
            isIncludeAndroidResources = true
        }
    }

    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain {
            kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")

            dependencies {
                implementation(libs.decompose)
                implementation(libs.koin.core)
                implementation(libs.koin.annotations)
                implementation(libs.kotlinx.immutableCollections)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.dateTime)
                implementation(libs.jetbrains.compose.runtime)

                implementation(projects.shared.network.graphql)
                implementation(projects.shared.network.rest)
                implementation(projects.shared.persistence)
                implementation(libs.futured.arkitekt.decompose)
                implementation(libs.futured.arkitekt.decomposeAnnotation)
                implementation(libs.futured.arkitekt.crUseCases)
                implementation(projects.shared.kmpResources)

                implementation(libs.logging.kermit)
                implementation(libs.skie.annotations)
                implementation(libs.network.ktor.http)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        androidMain {
            dependencies {
                implementation(project.dependencies.platform(libs.androidx.compose.bom))
            }
        }
    }
}

koinCompiler {
    userLogs.set(true)
}
