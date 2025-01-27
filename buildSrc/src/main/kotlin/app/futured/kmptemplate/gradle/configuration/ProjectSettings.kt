package app.futured.kmptemplate.gradle.configuration

import org.gradle.api.JavaVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

object ProjectSettings {

    object Gradle {
        const val TaskGroup = "futured"
    }

    object Kotlin {
        const val JvmToolchainVersion = 17
    }

    object Android {
        const val MinSdkVersion = 29
        const val TargetSdkVersion = 34
        const val CompileSdkVersion = 34

        const val ApplicationId = "app.futured.kmptemplate.android"

        val VersionCode = System.getenv("BUILD_NUMBER")?.toInt() ?: 1
        val VersionName = System.getenv("VERSION_NAME") ?: "1.0.0"

        val JavaCompatibility = JavaVersion.VERSION_11
        val KotlinJvmTarget = JvmTarget.JVM_11
        const val KotlinJvmTargetNum = "11"

        object BuildTypes {
            const val Debug = "debug"
            const val Enterprise = "enterprise"
            const val Release = "release"
        }

        object Signing {
            object Debug {
                val StorePassword = "android"
                val KeyAlias = "androiddebugkey"
                val KeyPassword = "android"
            }

            object Release {
                val StorePassword = System.getenv("RELEASE_KEYSTORE_PASS") ?: ""
                val KeyAlias = System.getenv("RELEASE_KEY_ALIAS") ?: ""
                val KeyPassword = System.getenv("RELEASE_KEY_PASS") ?: ""
            }
        }
    }

    object IOS {
        const val FrameworkName = "shared"
        const val FrameworkBundleId = "app.futured.kmptemplate.shared"
        const val MokoBaseLocalizationRegion = "en"
    }
}
