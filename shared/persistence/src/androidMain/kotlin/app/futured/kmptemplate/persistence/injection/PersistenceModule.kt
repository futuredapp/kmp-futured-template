package app.futured.kmptemplate.persistence.injection

import android.content.Context
import app.futured.kmptemplate.persistence.tools.SETTINGS_DATASTORE_FILENAME
import okio.Path
import okio.Path.Companion.toPath
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Single
@Named("DataStoreFilePath")
internal fun dataStoreFilePath(context: Context): Path =
    context.filesDir.resolve(SETTINGS_DATASTORE_FILENAME).absolutePath.toPath()
