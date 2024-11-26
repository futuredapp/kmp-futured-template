package app.futured.kmptemplate.android.tools.binding

import app.futured.kmptemplate.platform.binding.PlatformFirebaseCrashlytics

class PlatformFirebaseCrashlyticsImpl : PlatformFirebaseCrashlytics {

    override fun logMessage(message: String) {
        // TODO PROJECT-SETUP Uncomment when Firebase Crashlytics is added as dependency
        // Firebase.crashlytics.log(message)
    }

    override fun sendNonFatalException(error: Throwable) {
        // TODO PROJECT-SETUP Uncomment when Firebase Crashlytics is added as dependency
        // Firebase.crashlytics.recordException(error)
    }
}
