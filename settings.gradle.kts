pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
    }
}

rootProject.name = "KMP_Futured_template"

// https://docs.gradle.org/8.1.1/userguide/declaring_dependencies.html#sec:type-safe-project-accessors
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":androidApp")
include(":shared:arkitekt-decompose")
include(":shared:arkitekt-decompose:annotation")
// this removes compiler warning about same KLIB name https://youtrack.jetbrains.com/projects/KT/issues/KT-66568/w-KLIB-resolver-The-same-uniquename...-found-in-more-than-one-library
project(":shared:arkitekt-decompose:annotation").name = "arkitekt-annotation"
include(":shared:arkitekt-decompose:processor")
include(":shared:arkitekt-cr-usecases")
include(":shared:app")
include(":shared:network:graphql")
include(":shared:network:rest")
include(":shared:feature")
include(":shared:persistence")
include(":shared:platform")
include(":shared:resources")
// this removes compiler warning about same KLIB name https://youtrack.jetbrains.com/projects/KT/issues/KT-66568/w-KLIB-resolver-The-same-uniquename...-found-in-more-than-one-library
project(":shared:resources").name = "kmp-resources"
include(":shared:ui")
include(":baselineprofile")

includeBuild("convention-plugins")
