package app.futured.kmptemplate.android.tools.binding

import android.content.Context
import android.os.Build
import app.futured.kmptemplate.android.BuildConfig
import app.futured.kmptemplate.platform.binding.Platform
import app.futured.kmptemplate.resources.MR

class PlatformImpl(applicationContext: Context) : Platform {
    override val appName: String = MR.strings.app_name.getString(applicationContext)
    override val appVersionName: String = BuildConfig.VERSION_NAME
    override val appBuildNumber: String = BuildConfig.VERSION_CODE.toString()
    override val applicationId: String = BuildConfig.APPLICATION_ID
    override val osNameWithVersion: String = "Android ${Build.VERSION.RELEASE}"
    override val deviceModel: String = Build.MODEL
}
