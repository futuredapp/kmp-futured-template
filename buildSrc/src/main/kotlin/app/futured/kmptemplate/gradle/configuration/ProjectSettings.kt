package app.futured.kmptemplate.gradle.configuration

import org.gradle.api.JavaVersion

object ProjectSettings {

    object Gradle {
        const val TaskGroup = "futured"
    }

    object Kotlin {
        const val JvmToolchainVersion = 17
    }

    object Android {
        const val MinSdkVersion = 29
        const val TargetSdkVersion = 35
        const val CompileSdkVersion = 35

        const val ApplicationId = "app.futured.kmptemplate.android"

        val JavaCompatibility = JavaVersion.VERSION_17
        const val KotlinJvmTargetNum = "17"

        object BuildTypes {
            const val Debug = "debug"
            const val Enterprise = "enterprise"
            const val Release = "release"

            val all = listOf(Debug, Enterprise, Release)
        }

        object Signing {
            object Debug {
                val StorePassword = "android"
                val KeyAlias = "androiddebugkey"
                val KeyPassword = "android"
            }
        }
    }

    object IOS {
        const val FrameworkName = "KMP"
        const val FrameworkBundleId = "app.futured.kmptemplate.shared"
        const val MokoBaseLocalizationRegion = "en"
    }
}
