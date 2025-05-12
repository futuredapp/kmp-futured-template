package app.futured.kmptemplate.persistence.platform

import android.content.Context
import app.futured.kmptemplate.persistence.tools.SETTINGS_DATASTORE_FILENAME
import okio.Path
import okio.Path.Companion.toPath
import org.koin.core.annotation.Factory

@Factory
actual class PlatformComponent(val context: Context) {
    actual fun getDatastorePath(): Path = context.filesDir.resolve(SETTINGS_DATASTORE_FILENAME).absolutePath.toPath()
}
