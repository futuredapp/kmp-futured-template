package app.futured.kmptemplate.android.tools.intent

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.core.content.ContextCompat
import app.futured.kmptemplate.android.BuildConfig

object Intents {

    fun systemSettings(): Intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        .setData(Uri.fromParts("package", BuildConfig.APPLICATION_ID, null))

    fun openSocialApp(context: Context, openAppUrl: String, openWebUrl: String) {
        try {
            ContextCompat.startActivity(
                context,
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(openAppUrl),
                ),
                null,
            )
        } catch (e: Exception) {
            runCatching {
                ContextCompat.startActivity(
                    context,
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(openWebUrl),
                    ),
                    null,
                )
            }
        }
    }
}
