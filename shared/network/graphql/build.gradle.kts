import app.futured.kmptemplate.gradle.configuration.ProductFlavors
import app.futured.kmptemplate.gradle.configuration.ProjectSettings
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.apollo)
    alias(libs.plugins.buildkonfig)
    alias(libs.plugins.koin)

    id(libs.plugins.conventions.lint.get().pluginId)
}

kotlin {
    jvmToolchain(ProjectSettings.Kotlin.JvmToolchainVersion)

    android {
        namespace = libs.versions.project.shared.network.graphql.namespace.get()
        compileSdk = ProjectSettings.Android.CompileSdkVersion
        minSdk = ProjectSettings.Android.MinSdkVersion
    }

    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain {
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

koinCompiler {
    userLogs.set(true)
}
