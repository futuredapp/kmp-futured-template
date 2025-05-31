package app.futured.kmptemplate.android.tools.utils

import android.content.ContentResolver
import android.net.Uri
import java.io.IOException

@Throws(IOException::class)
fun readBytes(contentResolver: ContentResolver, uri: Uri): ByteArray? =
    contentResolver.openInputStream(uri)?.use { it.buffered().readBytes() }
