package app.futured.kmptemplate.android

import android.app.Application
import app.futured.kmptemplate.android.tools.binding.PlatformBindingsImpl
import org.koin.android.ext.koin.androidContext
import timber.log.Timber
import app.futured.kmptemplate.app.KmpApplication as SharedApp

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeKmp()
        initializeLogging()
    }

    private fun initializeKmp() {
        SharedApp.initializeSharedApplication(
            platformBindings = PlatformBindingsImpl()
        ) {
            androidContext(this@App)
        }
    }

    private fun initializeLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
