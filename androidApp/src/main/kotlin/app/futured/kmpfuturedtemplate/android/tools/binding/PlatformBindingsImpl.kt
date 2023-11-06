package app.futured.kmpfuturedtemplate.android.tools.binding

import app.futured.kmptemplate.platform.binding.PlatformBindings
import app.futured.kmptemplate.platform.binding.PlatformFirebaseCrashlytics

class PlatformBindingsImpl : PlatformBindings {
    override fun firebaseCrashlytics(): PlatformFirebaseCrashlytics = PlatformFirebaseCrashlyticsImpl()
}
