import app.futured.kmptemplate.gradle.configuration.ProjectSettings
import com.android.build.api.dsl.ManagedVirtualDevice

plugins {
    id(libs.plugins.android.test.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    alias(libs.plugins.androidx.baselineprofile)
}

android {
    namespace = libs.versions.project.baselineprofile.namespace.get()
    compileSdk = ProjectSettings.Android.CompileSdkVersion

    compileOptions {
        sourceCompatibility = ProjectSettings.Android.JavaCompatibility
        targetCompatibility = ProjectSettings.Android.JavaCompatibility
    }

    kotlinOptions {
        jvmTarget = ProjectSettings.Android.KotlinJvmTarget
    }

    defaultConfig {
        minSdk = ProjectSettings.Android.MinSdkVersion
        targetSdk = ProjectSettings.Android.TargetSdkVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    targetProjectPath = ":androidApp"

    // This code creates the gradle managed device used to generate baseline profiles.
    // To use GMD please invoke generation through the command line:
    // ./gradlew :androidApp:generateBaselineProfile
    testOptions.managedDevices.devices {
        create<ManagedVirtualDevice>("pixel6Api34") {
            device = "Pixel 6"
            apiLevel = 34
            systemImageSource = "google"
        }
    }
}

// This is the configuration block for the Baseline Profile plugin.
// You can specify to run the generators on a managed devices or connected devices.
baselineProfile {
    managedDevices += "pixel6Api34"
    useConnectedDevices = false
}

dependencies {
    implementation(libs.androidx.junit)
    implementation(libs.androidx.espresso.core)
    implementation(libs.androidx.uiautomator)
    implementation(libs.androidx.benchmark.macro.junit4)
}

androidComponents {
    onVariants { v ->
        val artifactsLoader = v.artifacts.getBuiltArtifactsLoader()
        v.instrumentationRunnerArguments.put(
            "targetAppId",
            v.testedApks.map { artifactsLoader.load(it)?.applicationId }
        )
    }
}
