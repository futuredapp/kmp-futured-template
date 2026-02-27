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
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.skie)
    alias(libs.plugins.moko.resources)

    id(libs.plugins.conventions.lint.get().pluginId)
    id(libs.plugins.conventions.annotationProcessing.get().pluginId)
}

annotations {
    useKoin = true
    androidBuildTypes = ProjectSettings.Android.BuildTypes.all
}

kotlin {
    jvmToolchain(ProjectSettings.Kotlin.JvmToolchainVersion)
    applyDefaultHierarchyTemplate()

    // Turns off warnings about beta feature https://youtrack.jetbrains.com/issue/KT-61573
    compilerOptions.freeCompilerArgs.add("-Xexpect-actual-classes")

    android {
        namespace = libs.versions.project.shared.app.namespace.get()
        compileSdk = ProjectSettings.Android.CompileSdkVersion
        minSdk = ProjectSettings.Android.MinSdkVersion
        androidResources { enable = true }
    }

    val xcf = XCFramework(ProjectSettings.IOS.FrameworkName)

    // Controls whether the KMP XCFramework is built as static or dynamic.
    // Dynamic (false) is needed for SwiftUI previews in Xcode (Debug builds).
    // Static (true, default) is used for Beta and Release builds.
    // Controlled via -PisStatic=true|false Gradle property, set from KMP_IS_STATIC in .xcconfig files.
    val isStaticFramework = project.findProperty(ProjectSettings.IOS.IsStaticFrameworkProperty)?.toString()?.toBoolean() ?: true

    listOf(
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = ProjectSettings.IOS.FrameworkName
            binaryOptions += "bundleId" to ProjectSettings.IOS.FrameworkBundleId
            isStatic = isStaticFramework

            export(projects.shared.platform)
            export(projects.shared.arkitektDecompose)
            export(projects.shared.feature)
            export(projects.shared.kmpResources)
            export(projects.shared.ui)

            export(libs.decompose)
            export(libs.essenty)
            export(libs.kotlinx.immutableCollections)
            export(libs.moko.resources)

            linkerOpts("-lsqlite3")

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
                implementation(projects.shared.kmpResources)

                implementation(libs.decompose)
                implementation(libs.koin.core)
                implementation(libs.koin.annotations)
                implementation(libs.logging.kermit)
                implementation(libs.logging.kermitCrashlytics)
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
                api(projects.shared.kmpResources)
                api(projects.shared.ui)

                api(libs.decompose)
                api(libs.kotlinx.immutableCollections)

                implementation(libs.logging.nsExceptionKt.core)
            }
        }
    }
}

skie {
    analytics {
        // https://skie.touchlab.co/Analytics
        disableUpload.set(false)
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
        if (iosDirectory.asFile.exists()) {
            iosDirectory.asFile.deleteRecursively()
        }
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
