package app.futured.kmpfuturedtemplate.android

import android.app.Application
import app.futured.kmpfuturedtemplate.android.injection.NativePlatformModuleImpl
import org.koin.android.ext.koin.androidContext
import app.futured.kmptemplate.app.KmpApplication as SharedApp

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        SharedApp.initializeSharedApplication(
            nativePlatformModule = NativePlatformModuleImpl(),
        ) {
            androidContext(this@App)
        }
    }
}
