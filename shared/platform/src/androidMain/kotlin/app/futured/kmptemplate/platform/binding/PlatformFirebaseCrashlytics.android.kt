package app.futured.kmptemplate.platform.binding

/**
 * Binding used by Kotlin to report handled an unhandled errors to native Crashlytics SDK.
 */
actual interface PlatformFirebaseCrashlytics {
    /**
     * Logs a message to Crashlytics SDK.
     */
    fun logMessage(message: String)

    /**
     * Reports a non-fatal [error][Throwable] to Crashlytics SDK.
     */
    fun sendNonFatalException(error: Throwable)
}
