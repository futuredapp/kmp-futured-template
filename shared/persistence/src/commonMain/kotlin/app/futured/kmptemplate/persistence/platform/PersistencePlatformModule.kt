package app.futured.kmptemplate.persistence.platform

import okio.Path
import org.koin.core.annotation.Factory
import org.koin.core.scope.Scope

expect class PlatformModule() {
    @Factory
    fun providePlatformComponent(scope: Scope): PlatformComponent
}

interface PlatformComponent {
    fun getDatastorePath(): Path
}
