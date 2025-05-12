package app.futured.kmptemplate.persistence.platform

import okio.Path
import org.koin.core.annotation.Factory

@Factory
expect class PlatformComponent {
    fun getDatastorePath(): Path
}
