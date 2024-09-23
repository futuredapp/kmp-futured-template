import app.futured.kmptemplate.gradle.configuration.ProductFlavors
import app.futured.kmptemplate.gradle.configuration.ProjectSettings
import app.futured.kmptemplate.gradle.ext.iosTargets
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING

plugins {
    id(libs.plugins.com.android.library.get().pluginId)
    id(libs.plugins.kotlin.multiplatform.get().pluginId)
    id(libs.plugins.conventions.lint.get().pluginId)
    id(libs.plugins.koin.annotations.plugin.get().pluginId)

    alias(libs.plugins.apollo)
    alias(libs.plugins.buildkonfig)
}

kotlin {
    jvmToolchain(ProjectSettings.Kotlin.JvmToolchainVersion)

    androidTarget {
        compilerOptions {
            jvmTarget.set(ProjectSettings.Android.KotlinJvmTarget)
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
                implementation(libs.bundles.apollo)
                implementation(libs.logging.kermit)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}

android {
    namespace = libs.versions.project.shared.network.graphql.namespace.get()
    compileSdk = ProjectSettings.Android.CompileSdkVersion
    defaultConfig {
        minSdk = ProjectSettings.Android.MinSdkVersion
    }
    compileOptions {
        sourceCompatibility = ProjectSettings.Android.JavaCompatibility
        targetCompatibility = ProjectSettings.Android.JavaCompatibility
    }
}

apollo {
    service("ExampleRickAndMortyService") {
        packageName.set(libs.versions.project.shared.network.graphql.packageName.get())
        schemaFile.set(file("src/commonMain/graphql/schema.graphqls"))

        introspection {
            endpointUrl.set("https://rickandmortyapi.com/graphql")
            schemaFile.set(file("src/commonMain/graphql/schema.graphqls"))
        }
    }
}

buildkonfig {
    packageName = libs.versions.project.shared.network.graphql.packageName.get()
    objectName = "FlavorConstants"

    defaultConfigs {
        buildConfigField(STRING, "apiUrl", ProductFlavors.DEFAULT.apolloApiUrl)
    }

    listOf(
        ProductFlavors.Dev,
        ProductFlavors.Prod,
    ).forEach {
        defaultConfigs(flavor = it.name) {
            buildConfigField(STRING, "apiUrl", it.apolloApiUrl)
        }
    }
}
