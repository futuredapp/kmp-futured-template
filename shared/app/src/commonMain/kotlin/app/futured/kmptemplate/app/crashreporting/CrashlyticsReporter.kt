package app.futured.kmptemplate.app.crashreporting

import app.futured.kmptemplate.platform.binding.PlatformFirebaseCrashlytics

/**
 * Class responsible for recording crashlytics errors using provided [PlatformFirebaseCrashlytics] binding.
 */
internal expect class CrashlyticsReporter(crashlytics: PlatformFirebaseCrashlytics) {
    fun logMessage(message: String)
    fun sendNonFatalException(throwable: Throwable)
}
