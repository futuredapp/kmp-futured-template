plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(libs.gradlePlugin.kotlin)
    implementation(libs.gradlePlugin.android)
    implementation(libs.gradlePlugin.ktlint)
    implementation(libs.gradlePlugin.detekt)
    implementation(libs.gradlePlugin.apollo)
}
