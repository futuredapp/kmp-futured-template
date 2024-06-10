package app.futured.kmptemplate.persistence.persistence

import androidx.datastore.preferences.core.booleanPreferencesKey
import org.koin.core.annotation.Single

interface ApplicationSettingsPersistence {
    suspend fun setDummyValue(value: Boolean)
}

@Single
internal class ApplicationSettingsPersistenceImpl(
    private val primitivePersistence: PrimitivePersistence,
) : ApplicationSettingsPersistence {

    override suspend fun setDummyValue(value: Boolean) {
        primitivePersistence.save(booleanPreferencesKey("dummy"), value)
    }
}
