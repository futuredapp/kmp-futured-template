package app.futured.kmptemplate.android.tools.utils

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

fun Activity.saveAndShareImage(
    imageUrl: String,
    coroutineScope: CoroutineScope,
    onLoadingStarted: () -> Unit,
    onLoadingFinished: () -> Unit,
    onError: () -> Unit,
) {
    coroutineScope.launch {
        withContext(Dispatchers.IO) {
            try {
                withContext(Dispatchers.Main) {
                    onLoadingStarted()
                }
                val bitmap = getBitmapFromURL(imageUrl)
                val uri = saveImageInAndroidApi29AndAbove(bitmap)
                withContext(Dispatchers.Main) {
                    onLoadingFinished()
                    shareImage(uri)
                }
            } catch (e: Throwable) {
                withContext(Dispatchers.Main) {
                    onLoadingFinished()
                    onError()
                }
            }
        }
    }
}

private fun getBitmapFromURL(src: String?): Bitmap {
    val url = URL(src)
    val connection = url.openConnection() as HttpURLConnection
    connection.doInput = true
    connection.connect()
    val input = connection.inputStream
    val myBitmap = BitmapFactory.decodeStream(input)
    return myBitmap
}

private fun Activity.saveImageInAndroidApi29AndAbove(bitmap: Bitmap): Uri {
    val values = ContentValues()
    values.put(MediaStore.MediaColumns.DISPLAY_NAME, "mDevCamp_" + System.currentTimeMillis())
    values.put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
    values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)

    val resolver = contentResolver
    var uri: Uri? = null
    try {
        val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        uri = resolver.insert(contentUri, values)
        if (uri == null) {
            throw IOException("Failed to create new MediaStore record.")
        }
        resolver.openOutputStream(uri).use { stream ->
            if (stream == null) {
                throw IOException("Failed to open output stream.")
            }
            if (!bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)) {
                throw IOException("Failed to save bitmap.")
            }
        }
        return uri
    } catch (e: IOException) {
        if (uri != null) {
            resolver.delete(uri, null, null)
        }
        throw e
    }
}

private fun Activity.shareImage(uri: Uri) {
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_STREAM, uri)
        type = "image/png"
    }
    startActivity(Intent.createChooser(shareIntent, "Share mDevCamp avatar"))
}
