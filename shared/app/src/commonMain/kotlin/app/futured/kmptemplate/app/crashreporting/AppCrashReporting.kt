package app.futured.kmptemplate.app.crashreporting

/**
 * Initializes Kotlin crashlytics on all KMP target platforms by intercepting unhandled exception hook if needed
 * and reporting errors to native Crashlytics SDK using provided [CrashlyticsReporter] class.
 */
internal object AppCrashReporting {

    fun initialize(crashlyticsReporter: CrashlyticsReporter) {
        setupKotlinCrashlytics(crashlyticsReporter)
    }
}

/**
 * Initializes unhandled exception hook according to each platform's requirements.
 */
internal expect fun setupKotlinCrashlytics(crashlytics: CrashlyticsReporter)
