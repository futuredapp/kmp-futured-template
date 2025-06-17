package app.futured.kmptemplate.persistence.platform

import android.content.Context
import app.futured.kmptemplate.persistence.tools.SETTINGS_DATASTORE_FILENAME
import okio.Path
import okio.Path.Companion.toPath
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.scope.Scope

@Module
actual class PlatformModule {

    @Factory
    actual fun providePlatformComponent(scope: Scope): PlatformComponent = PlatformComponentAndroid(scope)
}

class PlatformComponentAndroid(scope: Scope) : PlatformComponent {

    private val context: Context = scope.get()

    override fun getDatastorePath(): Path = context.filesDir.resolve(SETTINGS_DATASTORE_FILENAME).absolutePath.toPath()
}
