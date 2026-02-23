import app.futured.kmptemplate.gradle.configuration.ProjectSettings
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.androidx.baselineprofile)
    // TODO PROJECT-SETUP enable after providing google-services.json
    // alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.distribution)

    id(libs.plugins.conventions.lint.get().pluginId)
}

kotlin {
    jvmToolchain(ProjectSettings.Kotlin.JvmToolchainVersion)

    compilerOptions {
        jvmTarget.set(JvmTarget.fromTarget(ProjectSettings.Android.KotlinJvmTargetNum))
    }
}

android {
    namespace = libs.versions.project.android.namespace.get()
    compileSdk = ProjectSettings.Android.CompileSdkVersion

    defaultConfig {
        applicationId = ProjectSettings.Android.ApplicationId
        minSdk = ProjectSettings.Android.MinSdkVersion
        targetSdk = ProjectSettings.Android.TargetSdkVersion
        versionCode = System.getenv("ANDROID_BUILD_NUMBER")?.toIntOrNull() ?: 1
        versionName = System.getenv("ANDROID_VERSION_NAME") ?: "1.x.x-local"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    signingConfigs {
        getByName(ProjectSettings.Android.BuildTypes.Debug) {
            storeFile = file("keystore/debug.keystore")
            storePassword = ProjectSettings.Android.Signing.Debug.StorePassword
            keyAlias = ProjectSettings.Android.Signing.Debug.KeyAlias
            keyPassword = ProjectSettings.Android.Signing.Debug.KeyPassword
        }
        create(ProjectSettings.Android.BuildTypes.Release) {
            storeFile = file("keystore/todo_your_release_keystore.keystore")
            storePassword = System.getenv("ANDROID_KEYSTORE_PASSWORD").orEmpty()
            keyAlias = System.getenv("ANDROID_KEY_ALIAS").orEmpty()
            keyPassword = System.getenv("ANDROID_KEY_PASSWORD").orEmpty()
        }
    }

    buildTypes {
        getByName(ProjectSettings.Android.BuildTypes.Debug) {
            applicationIdSuffix = ".debug"
            isMinifyEnabled = false
            isShrinkResources = false
        }
        create(ProjectSettings.Android.BuildTypes.Enterprise) {
            isMinifyEnabled = true
            isShrinkResources = true

            proguardFile(getDefaultProguardFile("proguard-android-optimize.txt"))
            proguardFile(file("proguard-rules.pro"))

            signingConfig = signingConfigs.getByName(ProjectSettings.Android.BuildTypes.Debug)
            matchingFallbacks.add(ProjectSettings.Android.BuildTypes.Release)

            applicationIdSuffix = ".enterprise"
        }
        getByName(ProjectSettings.Android.BuildTypes.Release) {
            isMinifyEnabled = true
            isShrinkResources = true

            proguardFile(getDefaultProguardFile("proguard-android-optimize.txt"))
            proguardFile(file("proguard-rules.pro"))

            signingConfig = signingConfigs.getByName(ProjectSettings.Android.BuildTypes.Release)
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = ProjectSettings.Android.JavaCompatibility
        targetCompatibility = ProjectSettings.Android.JavaCompatibility
    }

    lint {
        textReport = true // Write a text report to the console (Useful for CI logs)
        xmlReport = true // Write XML report
        abortOnError = false // Do not abort build when error is found -> Danger will report this to the MR
        explainIssues = false // HTML/XML reports are too verbose in console logs
        checkDependencies = false // Required to get all unused resource from other modules (disabled to speed up linting)
        checkTestSources = true // Also check test case code for lint issues
        checkReleaseBuilds = false // If we run a full lint analysis as build part in CI, we can skip redundant checks
    }
}

dependencies {
    coreLibraryDesugaring(libs.androidTools.desugarLibs)
    lintChecks(libs.lint.compose)

    implementation(projects.shared.app)
    implementation(projects.shared.feature)
    implementation(projects.shared.platform)
    implementation(projects.shared.arkitektDecompose)
    implementation(projects.shared.resources)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)

    implementation(libs.androidx.activity)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.compose)

    implementation(libs.decompose)
    implementation(libs.decompose.compose.ext)

    implementation(libs.kotlinx.immutableCollections)
    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.koin.android)
    implementation(libs.logging.timber)

    implementation(libs.androidx.profileinstaller)
    baselineProfile(projects.baselineprofile)
}
