package app.futured.kmptemplate.android.tools.compose

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import app.futured.kmptemplate.android.R
import java.io.File
import java.util.Objects

class ComposeFileProvider : FileProvider(
    R.xml.path_provider,
) {
    companion object {
        fun getImageUri(context: Context): Uri {
            val tempFile = File.createTempFile(
                "picture_${System.currentTimeMillis()}",
                ".png",
                context.externalCacheDir,
            ).apply {
                createNewFile()
            }
            val authority = context.applicationContext.packageName + ".provider"
            return getUriForFile(
                Objects.requireNonNull(context),
                authority,
                tempFile,
            )
        }
    }
}
