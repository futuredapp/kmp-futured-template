pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "KMP_Futured_template"

// https://docs.gradle.org/8.1.1/userguide/declaring_dependencies.html#sec:type-safe-project-accessors
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":androidApp")
include(":shared:arkitekt-decompose")
include(":shared:arkitekt-decompose:annotation")
include(":shared:arkitekt-decompose:processor")
include(":shared:arkitekt-cr-usecases")
include(":shared:app")
include(":shared:network:rest")
include(":shared:feature")
include(":shared:persistence")
include(":shared:platform")
include(":shared:resources")
include(":baselineprofile")

includeBuild("convention-plugins")
