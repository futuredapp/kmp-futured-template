package app.futured.kmptemplate.persistence.injection

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import app.futured.kmptemplate.persistence.persistence.JsonPersistence
import app.futured.kmptemplate.persistence.persistence.PrimitivePersistence
import app.futured.kmptemplate.persistence.persistence.user.UserPersistence
import app.futured.kmptemplate.persistence.persistence.user.UserPersistenceImpl
import app.futured.kmptemplate.persistence.platform.PlatformComponent
import kotlinx.serialization.json.Json
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module(includes = [PersistencePlatformModule::class])
class PersistenceModule {

    @Single
    internal fun provideDataStore(
        platformComponent: PlatformComponent,
    ): DataStore<Preferences> = PreferenceDataStoreFactory.createWithPath(
        produceFile = { platformComponent.getDatastorePath() },
    )

    @Single
    @PersistenceJson
    internal fun provideJson(): Json = Json {
        encodeDefaults = true
        isLenient = false
        ignoreUnknownKeys = true
        prettyPrint = false
    }

    @Single
    internal fun providePrimitivePersistence(dataStore: DataStore<Preferences>): PrimitivePersistence = PrimitivePersistence(dataStore)

    @Single
    internal fun provideJsonPersistence(
        dataStore: DataStore<Preferences>,
        @PersistenceJson json: Json,
    ): JsonPersistence = JsonPersistence(dataStore, json)

    @Single
    internal fun provideUserPersistence(
        primitivePersistence: PrimitivePersistence,
    ): UserPersistence = UserPersistenceImpl(primitivePersistence)
}

@Module
@ComponentScan("app.futured.kmptemplate.persistence.platform")
class PersistencePlatformModule
