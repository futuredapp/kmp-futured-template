package app.futured.kmptemplate.persistence.persistence

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.mapNotNull

/**
 * [DataStore]-backed Persistence which allows storage and observing of persisted entities.
 */
internal class PrimitivePersistence(
    private val dataStore: DataStore<Preferences>,
) {

    suspend fun <T : Any> get(key: Preferences.Key<T>): T? = dataStore.data.firstOrNull()?.run {
        this[key]
    }

    fun <T : Any> observe(key: Preferences.Key<T>): Flow<T> = dataStore.data.mapNotNull {
        it[key]
    }

    suspend fun <T : Any> save(key: Preferences.Key<T>, value: T) {
        dataStore.edit { it[key] = value }
    }

    suspend fun delete(key: Preferences.Key<*>) = dataStore.edit {
        it.remove(key)
    }
}
