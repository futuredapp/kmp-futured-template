import app.futured.kmptemplate.gradle.configuration.ProductFlavors
import app.futured.kmptemplate.gradle.configuration.ProjectSettings
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktorfit)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.buildkonfig)

    id(libs.plugins.conventions.lint.get().pluginId)
    id(libs.plugins.annotations.processor.plugin.get().pluginId)
}

annotations {
    useKoin = true
}

kotlin {
    jvmToolchain(ProjectSettings.Kotlin.JvmToolchainVersion)

    androidTarget {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

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

                implementation(projects.shared.platform)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.network.ktor.client.engine.okhttp)
            }
        }

        iosMain {
            dependencies {
                implementation(libs.network.ktor.client.engine.darwin)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
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
    objectName = "FlavorConstants"

    defaultConfigs {
        buildConfigField(STRING, "apiUrl", ProductFlavors.DEFAULT.restApiUrl)
        buildConfigField(STRING, "ktorUserAgentVersion", libs.versions.ktor.get())
    }

    listOf(
        ProductFlavors.Dev,
        ProductFlavors.Prod,
    ).forEach {
        defaultConfigs(flavor = it.name) {
            buildConfigField(STRING, "apiUrl", it.restApiUrl)
            buildConfigField(STRING, "ktorUserAgentVersion", libs.versions.ktor.get())
        }
    }
}
