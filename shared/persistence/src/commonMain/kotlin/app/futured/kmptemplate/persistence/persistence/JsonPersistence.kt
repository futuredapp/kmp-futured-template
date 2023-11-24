package app.futured.kmptemplate.persistence.persistence

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * [DataStore]-backed Persistence which allows storage and observing of complex JSON objects.
 * Uses [kotlinx.serialization] to serialize and deserialize objects into Strings.
 */
internal class JsonPersistence(
    private val dataStore: DataStore<Preferences>,
    private val json: Json,
) {

    private val logger = Logger.withTag("JsonPersistence")

    /**
     * Saves provided [value] in persistence under provided [key].
     */
    suspend inline fun <reified T : Any> save(
        key: Preferences.Key<String>,
        value: T,
    ) {
        dataStore.edit { preferences -> preferences[key] = json.encodeToString<T>(value) }
    }

    /**
     * Returns persisted object with provided [key].
     *
     * @return Persisted object, or `null` if does not exist.
     * @throws [SerializationException] if object could not be deserialized as [T].
     */
    suspend inline fun <reified T : Any> get(key: Preferences.Key<String>): T? =
        dataStore.data.firstOrNull()
            ?.let { preferences -> preferences[key] }
            ?.let { jsonString ->
                json.decodeFromString<T>(jsonString)
            }

    /**
     * Returns a [Flow] of persisted objects with provided [key].
     *
     * @return [Flow] of objects. Values in [Flow] can be `null` if not found in persistence.
     * @throws [SerializationException] if object could not be deserialized as [T].
     */
    inline fun <reified T : Any> observe(key: Preferences.Key<String>): Flow<T?> =
        dataStore.data
            .map { preferences -> preferences[key] }
            .map { jsonString ->
                jsonString?.let { json.decodeFromString(jsonString) }
            }

    /**
     * Removes persisted object with provided [key].
     */
    suspend fun delete(key: Preferences.Key<String>) = dataStore.edit { preferences -> preferences.remove(key) }

    /**
     * Returns persisted object with provided [key], catching any [SerializationException]s and mapping them to `null`.
     *
     * @return Persisted object, or `null` if does not exist or object cannot be deserialized as [T].
     */
    suspend inline fun <reified T : Any> getCatching(key: Preferences.Key<String>): T? =
        runCatching { get<T>(key) }
            .recoverCatching { error ->
                if (error is SerializationException) {
                    logger.e(error) { "Unable to deserialize property with key '${key.name}'" }
                    null
                } else {
                    throw error
                }
            }
            .getOrThrow()

    /**
     * Returns a [Flow] of persisted objects with provided [key], catching any [SerializationException]s that might occur
     * and mapping them to `null` values.
     *
     * @return [Flow] of objects. Values in [Flow] can be `null` if not found in persistence or cannot be deserialized as [T].
     */
    inline fun <reified T : Any> observeCatching(key: Preferences.Key<String>): Flow<T?> =
        dataStore.data
            .map { preferences -> preferences[key] }
            .map { jsonString ->
                runCatching { jsonString?.let { json.decodeFromString<T>(jsonString) } }
                    .recoverCatching { error ->
                        if (error is SerializationException) {
                            logger.e(error) { "Unable to deserialize property with key '${key.name}'" }
                            null
                        } else {
                            null
                        }
                    }.getOrThrow()
            }
}
