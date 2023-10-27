import app.futured.kmptemplate.gradle.configuration.ProjectSettings
import app.futured.kmptemplate.gradle.ext.iosTargets
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING

plugins {
    id(libs.plugins.com.android.library.get().pluginId)
    id(libs.plugins.kotlin.multiplatform.get().pluginId)
    id(libs.plugins.conventions.lint.get().pluginId)

    alias(libs.plugins.apollo)
    alias(libs.plugins.buildkonfig)
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = ProjectSettings.Kotlin.JvmTarget
            }
        }
    }

    iosTargets()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.koin.core)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.bundles.apollo.bundle)
                implementation(libs.logging.kermit)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.testCommon)
                implementation(libs.kotlin.testAnnotationsCommon)
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

    with(ProjectSettings.Kotlin.ProductFlavors.Dev) {
        defaultConfigs {
            buildConfigField(STRING, "apiUrl", ApolloApiUrl)
        }

        defaultConfigs(flavor = NAME) {
            buildConfigField(STRING, "apiUrl", ApolloApiUrl)
        }
    }

    with(ProjectSettings.Kotlin.ProductFlavors.Prod) {
        defaultConfigs(flavor = NAME) {
            buildConfigField(STRING, "apiUrl", ApolloApiUrl)
        }
    }
}
