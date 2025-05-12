package app.futured.kmptemplate.persistence.injection

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import app.futured.kmptemplate.persistence.persistence.JsonPersistence
import app.futured.kmptemplate.persistence.persistence.PrimitivePersistence
import app.futured.kmptemplate.persistence.platform.PlatformComponent
import kotlinx.serialization.json.Json
import okio.Path
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

@Module(includes = [PersistencePlatformModule::class])
@ComponentScan("app.futured.kmptemplate.persistence")
class PersistenceModule {

    @Single
    internal fun provideDataStore(platformComponent: PlatformComponent): DataStore<Preferences> {
        return PreferenceDataStoreFactory.createWithPath(
            produceFile = { platformComponent.getDatastorePath() },
        )
    }

    @Single
    internal fun providePrimitivePersistence(dataStore: DataStore<Preferences>): PrimitivePersistence {
        return PrimitivePersistence(dataStore)
    }

    @Single
    internal fun provideJsonPersistence(
        dataStore: DataStore<Preferences>,
        @PersistenceJson json: Json,
    ): JsonPersistence {
        return JsonPersistence(dataStore, json)
    }

    @Single
    @PersistenceJson
    internal fun provideJson(): Json {
        return Json {
            encodeDefaults = true
            isLenient = false
            ignoreUnknownKeys = true
            prettyPrint = false
        }
    }
}

@Module
@ComponentScan("app.futured.kmptemplate.persistence.platform")
expect class PersistencePlatformModule()
