package app.futured.kmptemplate.persistence.injection

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import kotlinx.serialization.json.Json
import okio.Path
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Module
@ComponentScan("app.futured.kmptemplate.persistence")
class PersistenceModule {

    @Single
    internal fun dataStore(
        @Named("DataStoreFilePath") path: Path,
    ): DataStore<Preferences> = PreferenceDataStoreFactory.createWithPath(
        produceFile = { path },
    )

    @Single
    @Named("PersistenceJson")
    internal fun json() = Json {
        encodeDefaults = true
        isLenient = false
        ignoreUnknownKeys = true
        prettyPrint = false
    }
}
