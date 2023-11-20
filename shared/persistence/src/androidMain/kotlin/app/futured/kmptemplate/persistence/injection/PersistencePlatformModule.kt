package app.futured.kmptemplate.persistence.injection

import android.content.Context
import app.futured.kmptemplate.persistence.tools.SETTINGS_DATASTORE_FILENAME
import okio.Path
import okio.Path.Companion.toPath
import org.koin.core.annotation.Named

actual class PersistencePlatformModule(private val context: Context) {

    @Named("DataStoreFilePath")
    actual fun dataStoreFilePath(): Path {
        return context.filesDir.resolve(SETTINGS_DATASTORE_FILENAME).absolutePath.toPath()
    }
}
