package app.futured.kmptemplate.app.crashreporting

import app.futured.kmptemplate.platform.binding.PlatformFirebaseCrashlytics

/**
 * Class responsible for recording crashlytics errors using provided [PlatformFirebaseCrashlytics] binding.
 */
internal actual class CrashlyticsReporter actual constructor(private val crashlytics: PlatformFirebaseCrashlytics) {
    actual fun logMessage(message: String) = crashlytics.logMessage(message)
    actual fun sendNonFatalException(throwable: Throwable) = crashlytics.sendNonFatalException(throwable)
}
