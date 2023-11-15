package app.futured.kmptemplate.platform.binding

import platform.Foundation.NSException

/**
 * Binding used by Kotlin to report handled an unhandled errors to native Crashlytics SDK.
 */
actual interface PlatformFirebaseCrashlytics {

    /**
     * Logs a message to Crashlytics SDK.
     */
    fun logMessage(message: String)

    /**
     * Reports a provided non-fatal [nsException][NSException] to Crashlytics SDK.
     */
    fun sendNonFatalException(nsException: NSException)

    /**
     * Raises provided fatal [nsException][NSException] on native side.
     */
    fun raiseFatalException(nsException: NSException)
}
