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
include(":shared:app")
include(":shared:network:graphql")
include(":shared:network:rest")
include(":shared:feature")
include(":shared:persistence")
include(":shared:platform")
include(":shared:resources")
// this removes compiler warning about same KLIB name https://youtrack.jetbrains.com/projects/KT/issues/KT-66568/w-KLIB-resolver-The-same-uniquename...-found-in-more-than-one-library
project(":shared:resources").name = "kmp-resources"
include(":baselineprofile")

includeBuild("convention-plugins")
