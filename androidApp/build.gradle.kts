import app.futured.kmptemplate.gradle.configuration.ProjectSettings

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
}

android {
    namespace = libs.versions.project.android.namespace.get()
    compileSdk = ProjectSettings.Android.CompileSdkVersion

    defaultConfig {
        applicationId = ProjectSettings.Android.ApplicationId
        minSdk = ProjectSettings.Android.MinSdkVersion
        targetSdk = ProjectSettings.Android.TargetSdkVersion
        versionCode = ProjectSettings.Android.VersionCode
        versionName = ProjectSettings.Android.VersionName
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
            storePassword = ProjectSettings.Android.Signing.Release.StorePassword
            keyAlias = ProjectSettings.Android.Signing.Release.KeyAlias
            keyPassword = ProjectSettings.Android.Signing.Release.KeyPassword
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

    kotlinOptions {
        jvmTarget = ProjectSettings.Android.KotlinJvmTargetNum
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


    implementation(libs.coil.compose)
}
