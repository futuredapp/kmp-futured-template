package app.futured.kmptemplate.app.crashreporting

/**
 * Initializes unhandled exception hook according to each platform's requirements.
 * There's no need to intercept unhandled exception on Android, as Crashlytics works its magic natively here.
 */
internal actual fun setupKotlinCrashlytics(crashlytics: CrashlyticsReporter) = Unit
