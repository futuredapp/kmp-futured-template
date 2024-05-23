import app.futured.kmptemplate.gradle.configuration.ProjectSettings
import app.futured.kmptemplate.gradle.ext.iosTargets
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING

plugins {
    id(libs.plugins.com.android.library.get().pluginId)
    id(libs.plugins.kotlin.multiplatform.get().pluginId)
    id(libs.plugins.conventions.lint.get().pluginId)

    alias(libs.plugins.ksp)
    alias(libs.plugins.ktorfit)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.buildkonfig)
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
        commonMain {
            kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")

            dependencies {
                implementation(libs.koin.core)
                implementation(libs.koin.annotations)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.bundles.ktorfit)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.logging.kermit)
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

dependencies {
    /* ref:
    https://foso.github.io/Ktorfit/installation/
    https://github.com/Foso/Ktorfit/blob/master/example/MultiplatformExample/shared/build.gradle.kts
    */
    add("kspCommonMainMetadata", libs.network.ktorfit.ksp)
}

android {
    namespace = libs.versions.project.shared.network.rest.namespace.get()
    compileSdk = ProjectSettings.Android.CompileSdkVersion
    defaultConfig {
        minSdk = ProjectSettings.Android.MinSdkVersion
    }
    compileOptions {
        sourceCompatibility = ProjectSettings.Android.JavaCompatibility
        targetCompatibility = ProjectSettings.Android.JavaCompatibility
    }
}

buildkonfig {
    packageName = libs.versions.project.shared.network.rest.packageName.get()

    with(ProjectSettings.Kotlin.ProductFlavors.Dev) {
        defaultConfigs {
            buildConfigField(STRING, "apiUrl", RestApiUrl)
        }

        defaultConfigs(flavor = NAME) {
            buildConfigField(STRING, "apiUrl", RestApiUrl)
        }
    }

    with(ProjectSettings.Kotlin.ProductFlavors.Prod) {
        defaultConfigs(flavor = NAME) {
            buildConfigField(STRING, "apiUrl", RestApiUrl)
        }
    }
}

ksp {
    // enable compile time check
    arg("KOIN_CONFIG_CHECK","false")
    // disable default module generation
    arg("KOIN_DEFAULT_MODULE","false")
}

dependencies {
    add("kspCommonMainMetadata", libs.koin.ksp.compiler)
}

// WORKAROUND: This somehow makes ksp generate stuff. I have no fucking idea why.
tasks.withType<org.jetbrains.kotlin.gradle.dsl.KotlinCompile<*>>().configureEach {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}
