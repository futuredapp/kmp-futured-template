package app.futured.kmptemplate.persistence.injection

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import app.futured.kmptemplate.persistence.persistence.JsonPersistence
import app.futured.kmptemplate.persistence.persistence.PrimitivePersistence
import kotlinx.serialization.json.Json
import okio.Path
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Module([PersistencePlatformModule::class])
@ComponentScan("app.futured.kmptemplate.persistence")
class PersistenceModule {

    @Single
    fun preferenceDataStore(@Named("DataStoreFilePath") path: Path) = PreferenceDataStoreFactory.createWithPath(
        produceFile = { path },
    )

    @Single
    internal fun primitivePersistence(dataStore: DataStore<Preferences>) = PrimitivePersistence(dataStore)

    @Single
    internal fun jsonPersistence(
        dataStore: DataStore<Preferences>,
        @Named("PersistenceJson") json: Json,
    ) = JsonPersistence(dataStore, json)

    @Single
    @Named("PersistenceJson")
    fun persistenceJson() = Json {
        encodeDefaults = true
        isLenient = false
        ignoreUnknownKeys = true
        prettyPrint = false
    }
}
