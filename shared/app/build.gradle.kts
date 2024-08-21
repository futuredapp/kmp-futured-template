import app.futured.kmptemplate.gradle.configuration.ProjectSettings
import app.futured.kmptemplate.gradle.ext.iosTargets
import co.touchlab.skie.configuration.DefaultArgumentInterop
import co.touchlab.skie.configuration.EnumInterop
import co.touchlab.skie.configuration.FlowInterop
import co.touchlab.skie.configuration.SealedInterop
import co.touchlab.skie.configuration.SuppressSkieWarning
import co.touchlab.skie.configuration.SuspendInterop
import dev.icerock.gradle.MRVisibility

plugins {
    id(libs.plugins.com.android.library.get().pluginId)
    id(libs.plugins.kotlin.multiplatform.get().pluginId)
    id(libs.plugins.conventions.lint.get().pluginId)
    id(libs.plugins.koin.annotations.plugin.get().pluginId)

    alias(libs.plugins.skie)
    alias(libs.plugins.moko.resources)
}

kotlin {
    jvmToolchain(ProjectSettings.Kotlin.JvmToolchainVersion)
    applyDefaultHierarchyTemplate()

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = ProjectSettings.Android.KotlinJvmTarget
            }
        }
    }

    iosTargets {
        it.binaries.framework {
            baseName = ProjectSettings.IOS.FrameworkName
            binaryOptions += "bundleId" to ProjectSettings.IOS.FrameworkBundleId
            isStatic = true

            export(projects.shared.platform)
            export(projects.shared.util)
            export(projects.shared.feature)
            export(projects.shared.resources)

            export(libs.decompose)
            export(libs.essenty)
            export(libs.kotlinx.immutableCollections)
            export(libs.moko.resources)
        }
    }

    sourceSets {
        commonMain {
            kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")

            dependencies {
                implementation(projects.shared.platform)
                implementation(projects.shared.feature)
                implementation(projects.shared.network.graphql)
                implementation(projects.shared.network.rest)
                implementation(projects.shared.persistence)
                implementation(projects.shared.resources)

                implementation(libs.decompose)
                implementation(libs.koin.core)
                implementation(libs.koin.annotations)
                implementation(libs.logging.kermit)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        iosMain {
            dependencies {
                api(projects.shared.platform)
                api(projects.shared.util)
                api(projects.shared.feature)
                api(projects.shared.resources)

                api(libs.decompose)
                api(libs.kotlinx.immutableCollections)

                implementation(libs.logging.nsExceptionKt.core)
            }
        }
    }
}

android {
    namespace = libs.versions.project.shared.app.namespace.get()
    compileSdk = ProjectSettings.Android.CompileSdkVersion
    defaultConfig {
        minSdk = ProjectSettings.Android.MinSdkVersion
    }
    compileOptions {
        sourceCompatibility = ProjectSettings.Android.JavaCompatibility
        targetCompatibility = ProjectSettings.Android.JavaCompatibility
    }

    buildFeatures {
        buildConfig = true
    }
}

skie {
    analytics {
        disableUpload.set(true) // This is explicitly disabled, consider supporting authors by enabling.
    }
    features {
        group {
            DefaultArgumentInterop.Enabled(false)
            SuspendInterop.Enabled(true)
            FlowInterop.Enabled(true)
            EnumInterop.Enabled(true)
            SealedInterop.Enabled(true)
            SuppressSkieWarning.NameCollision(true)
        }
    }
}

multiplatformResources {
    // This module uses the plugin just to export resources to iOS.
    // Visibility is set to Internal in order to prevent :androidApp
    // module to see duplicate resources from this module and :shared:resources module.
    resourcesVisibility.set(MRVisibility.Internal)
    resourcesClassName.set("MR")
    iosBaseLocalizationRegion.set(ProjectSettings.IOS.MokoBaseLocalizationRegion)
}
