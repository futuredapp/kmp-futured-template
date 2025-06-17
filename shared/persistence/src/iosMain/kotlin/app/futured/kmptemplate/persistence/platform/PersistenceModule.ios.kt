package app.futured.kmptemplate.persistence.platform

import app.futured.kmptemplate.persistence.tools.SETTINGS_DATASTORE_FILENAME
import kotlinx.cinterop.ExperimentalForeignApi
import okio.Path
import okio.Path.Companion.toPath
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.scope.Scope
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

@Module
actual class PlatformModule {

    @Factory
    actual fun providePlatformComponent(scope: Scope): PlatformComponent = PlatformComponentIos()
}

class PlatformComponentIos : PlatformComponent {

    @OptIn(ExperimentalForeignApi::class)
    override fun getDatastorePath(): Path {
        val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )

        return (requireNotNull(documentDirectory).path + "/$SETTINGS_DATASTORE_FILENAME").toPath()
    }
}
