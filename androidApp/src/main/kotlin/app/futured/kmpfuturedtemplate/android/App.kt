package app.futured.kmpfuturedtemplate.android

import android.app.Application
import app.futured.kmptemplate.app.Application as SharedApp
import org.koin.android.ext.koin.androidContext

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        SharedApp.initializeSharedApplication {
            androidContext(this@App)
        }
    }
}
