import app.futured.kmptemplate.gradle.configuration.ProjectSettings
import co.touchlab.skie.configuration.DefaultArgumentInterop
import co.touchlab.skie.configuration.EnumInterop
import co.touchlab.skie.configuration.FlowInterop
import co.touchlab.skie.configuration.SealedInterop
import co.touchlab.skie.configuration.SuppressSkieWarning
import co.touchlab.skie.configuration.SuspendInterop
import dev.icerock.gradle.MRVisibility
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.skie)
    alias(libs.plugins.moko.resources)

    id(libs.plugins.conventions.lint.get().pluginId)
    id(libs.plugins.conventions.annotationProcessing.get().pluginId)
}

annotations {
    useKoin = true
}

kotlin {
    jvmToolchain(ProjectSettings.Kotlin.JvmToolchainVersion)
    applyDefaultHierarchyTemplate()

    androidTarget {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
        }
    }

    val xcf = XCFramework(ProjectSettings.IOS.FrameworkName)

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = ProjectSettings.IOS.FrameworkName
            binaryOptions += "bundleId" to ProjectSettings.IOS.FrameworkBundleId
            isStatic = true

            export(projects.shared.platform)
            export(projects.shared.arkitektDecompose)
            export(projects.shared.feature)
            export(projects.shared.resources)

            export(libs.decompose)
            export(libs.essenty)
            export(libs.kotlinx.immutableCollections)
            export(libs.moko.resources)

            xcf.add(this)
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
                api(projects.shared.arkitektDecompose)
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

private fun Copy.assembleAndCopySwiftPackageForBuildType(buildType: NativeBuildType) {
    group = ProjectSettings.Gradle.TaskGroup

    val frameworkName = ProjectSettings.IOS.FrameworkName
    val xcfDirectory = project.layout.buildDirectory.dir("XCFrameworks/${buildType.getName()}")
    val iosDirectory = rootProject.layout.projectDirectory.dir("iosApp/shared/KMP/Sources")

    doFirst {
        delete(iosDirectory)
    }

    // Produces assembleKMPDebugXCFramework or assembleKMPReleaseXCFramework depending on input build type
    val xcfTask = buildString {
        append("assemble")
        append(frameworkName.replaceFirstChar { it.titlecase() })
        append(buildType.getName().replaceFirstChar { it.titlecase() })
        append("XCFramework")
    }

    dependsOn(xcfTask)
    from(xcfDirectory)
    into(iosDirectory)
}

tasks.register<Copy>("assembleAndCopyDebugSwiftPackage") {
    assembleAndCopySwiftPackageForBuildType(NativeBuildType.DEBUG)
}

tasks.register<Copy>("assembleAndCopyReleaseSwiftPackage") {
    assembleAndCopySwiftPackageForBuildType(NativeBuildType.RELEASE)
}
