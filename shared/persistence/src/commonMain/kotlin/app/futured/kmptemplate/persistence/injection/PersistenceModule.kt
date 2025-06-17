package app.futured.kmptemplate.persistence.injection

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import app.futured.kmptemplate.persistence.persistence.JsonPersistence
import app.futured.kmptemplate.persistence.persistence.PrimitivePersistence
import app.futured.kmptemplate.persistence.persistence.user.UserPersistence
import app.futured.kmptemplate.persistence.persistence.user.UserPersistenceImpl
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Module
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single

@Module(includes = [DataStoreModule::class])
class PersistenceModule {

    @Single
    @PersistenceJson
    internal fun provideJson(): Json = Json {
        encodeDefaults = true
        isLenient = false
        ignoreUnknownKeys = true
        prettyPrint = false
    }

    @Single
    internal fun providePrimitivePersistence(
        @Provided dataStore: DataStore<Preferences>,
    ): PrimitivePersistence = PrimitivePersistence(dataStore)

    @Single
    internal fun provideJsonPersistence(
        @Provided dataStore: DataStore<Preferences>,
        @PersistenceJson json: Json,
    ): JsonPersistence = JsonPersistence(dataStore, json)

    @Single
    internal fun provideUserPersistence(
        primitivePersistence: PrimitivePersistence,
    ): UserPersistence = UserPersistenceImpl(primitivePersistence)
}

@Module
expect class DataStoreModule() {

    @Single
    fun provideDataStore(): DataStore<Preferences>
}
