package app.futured.kmptemplate.platform.binding

/**
 * This interface provides native binding interfaces which are used to interface between KMP and all its targets.
 * Each interface must be implemented natively on each platform.
 */
interface PlatformBindings {

    /**
     * Provides a native binding to Firebase Crashlytics SDK.
     */
    fun firebaseCrashlytics(): PlatformFirebaseCrashlytics
}
