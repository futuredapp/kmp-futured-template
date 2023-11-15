package app.futured.kmptemplate.app.crashreporting

import com.rickclephas.kmp.nsexceptionkt.core.wrapUnhandledExceptionHook

/**
 * Initializes unhandled exception hook according to each platform's requirements.
 */
internal actual fun setupKotlinCrashlytics(crashlytics: CrashlyticsReporter) = wrapUnhandledExceptionHook { throwable ->
    crashlytics.raiseFatalException(throwable)
}
