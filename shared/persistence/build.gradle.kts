import app.futured.kmptemplate.gradle.configuration.ProjectSettings
import app.futured.kmptemplate.gradle.ext.iosTargets

plugins {
    id(libs.plugins.com.android.library.get().pluginId)
    id(libs.plugins.kotlin.multiplatform.get().pluginId)
    id(libs.plugins.conventions.lint.get().pluginId)

    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
}

android {
    namespace = libs.versions.project.shared.persistence.namespace.get()
    compileSdk = ProjectSettings.Android.CompileSdkVersion
    defaultConfig {
        minSdk = ProjectSettings.Android.MinSdkVersion
    }
    compileOptions {
        sourceCompatibility = ProjectSettings.Android.JavaCompatibility
        targetCompatibility = ProjectSettings.Android.JavaCompatibility
    }
}

kotlin {
    jvmToolchain(ProjectSettings.Kotlin.JvmToolchainVersion)

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = ProjectSettings.Android.KotlinJvmTarget
            }
        }
    }

    iosTargets()

    sourceSets {
        androidMain {
            kotlin.srcDir("build/generated/ksp/android/androidDebug/kotlin")
            kotlin.srcDir("build/generated/ksp/android/androidRelease/kotlin")
        }
        iosMain {
            kotlin.srcDir("build/generated/ksp/iosArm64/iosArm64Main/kotlin")
            kotlin.srcDir("build/generated/ksp/iosSimulatorArm64/iosSimulatorArm64Main/kotlin")
            kotlin.srcDir("build/generated/ksp/iosX64/iosX64Main/kotlin")
        }
        commonMain {
            dependencies {
                implementation(libs.koin.core)
                implementation(libs.koin.annotations)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.logging.kermit)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.androidx.datastore.preferences.core)
            }
        }
        commonTest {
            dependencies {
                implementation(libs.kotlin.testCommon)
                implementation(libs.kotlin.testAnnotationsCommon)
            }
        }
    }
}

ksp {
    // enable compile time check
    arg("KOIN_CONFIG_CHECK", "false")
    // disable default module generation
    arg("KOIN_DEFAULT_MODULE", "false")
}

dependencies {
    add("kspAndroid", libs.koin.ksp.compiler)
    add("kspIosX64", libs.koin.ksp.compiler)
    add("kspIosArm64", libs.koin.ksp.compiler)
    add("kspIosSimulatorArm64", libs.koin.ksp.compiler)
}

tasks.matching { it.name == "compileIosMainKotlinMetadata" }.configureEach {
    if (project.tasks.findByName("kspKotlinIosSimulatorArm64") != null) {
        dependsOn("kspKotlinIosSimulatorArm64")
    }
}
