plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    val projectSettings = libs.versions.project

    namespace = projectSettings.android.namespace.get()
    compileSdk = projectSettings.compileSdk.get().toInt()
    defaultConfig {
        applicationId = projectSettings.appId.get()
        minSdk = projectSettings.minSdk.get().toInt()
        targetSdk = projectSettings.compileSdk.get().toInt()
        versionCode = projectSettings.versionCode.get().toInt()
        versionName = projectSettings.versionName.get()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = projectSettings.jvmTarget.get()
    }
}

dependencies {
    implementation(projects.shared.app)
    implementation(projects.shared.feature)

    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)

    implementation(libs.bundles.compose.bundle)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.activity.compose)

    implementation(libs.decompose)
    implementation(libs.decompose.compose.ext)

    implementation(libs.koin.android)
}
