plugins {
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.com.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
