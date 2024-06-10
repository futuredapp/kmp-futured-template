package app.futured.kmptemplate.android.tools.binding

import android.content.Context
import app.futured.kmptemplate.platform.binding.Platform
import app.futured.kmptemplate.platform.binding.PlatformBindings
import app.futured.kmptemplate.platform.binding.PlatformFirebaseCrashlytics

class PlatformBindingsImpl(private val applicationContext: Context) : PlatformBindings {
    override fun platform(): Platform = PlatformImpl(applicationContext)
    override fun firebaseCrashlytics(): PlatformFirebaseCrashlytics = PlatformFirebaseCrashlyticsImpl()
}
