package app.futured.kmptemplate.persistence.test

import androidx.datastore.preferences.core.intPreferencesKey
import app.futured.kmptemplate.persistence.persistence.PrimitivePersistence

interface TestRepository {
    suspend fun test(): Int
    suspend fun saveTest(value: Int)
}

internal class TestRepositoryImpl(
    private val primitivePersistence: PrimitivePersistence,
) : TestRepository {
    override suspend fun test(): Int {
        return primitivePersistence.get(intPreferencesKey("TEST")) ?: 0
    }

    override suspend fun saveTest(value: Int) {
        primitivePersistence.save(intPreferencesKey("TEST"), value)
    }
}
