package app.futured.kmptemplate.persistence.injection

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.serialization.json.Json
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module(includes = [DataStoreModule::class])
@ComponentScan("app.futured.kmptemplate.persistence")
class PersistenceModule {

    @Single
    @PersistenceJson
    internal fun provideJson(): Json = Json {
        encodeDefaults = true
        isLenient = false
        ignoreUnknownKeys = true
        prettyPrint = false
    }
}

@Module
expect class DataStoreModule() {

    @Single
    fun provideDataStore(): DataStore<Preferences>
}
