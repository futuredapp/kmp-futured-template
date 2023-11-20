package app.futured.kmptemplate.persistence.injection

import okio.Path
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named

@Module
expect class PersistencePlatformModule {

    @Named("DataStoreFilePath")
    fun dataStoreFilePath(): Path
}
