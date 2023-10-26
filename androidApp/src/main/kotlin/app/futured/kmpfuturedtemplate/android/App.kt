package app.futured.kmpfuturedtemplate.android

import android.app.Application
import app.futured.kmpfuturedtemplate.android.injection.NativePlatformModuleImpl
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
        SharedApp.initializeSharedApplication(nativePlatformModule = NativePlatformModuleImpl()) {
            androidContext(this@App)
        }
    }

    private fun initializeLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
