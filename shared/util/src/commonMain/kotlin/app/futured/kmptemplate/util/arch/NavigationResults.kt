package app.futured.kmptemplate.util.arch

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.random.Random

/**
 * A custom serializer for serializing [NavigationResultFlow] inside Decompose configuration classes.
 */
class NavigationResultFlowSerializer<T>(private val dataSerializer: KSerializer<T>) :
    KSerializer<MutableStateFlow<T>> {
    override val descriptor: SerialDescriptor = dataSerializer.descriptor
    override fun serialize(encoder: Encoder, value: MutableStateFlow<T>) =
        dataSerializer.serialize(encoder, value.value)

    override fun deserialize(decoder: Decoder) =
        MutableStateFlow(dataSerializer.deserialize(decoder))
}

/**
 * A class encapsulating navigation results.
 */
@Serializable
sealed class NavigationResult<out T : Any> {

    /**
     * Represents an initial state of navigation result.
     */
    @Serializable
    data object Empty : NavigationResult<Nothing>()

    /**
     * Represents a successful navigation result containing data.
     *
     * @param T The type of the data.
     * @property data The data of the navigation result.
     * @property seed A random seed for ensuring each new emission of data is propagated via [MutableStateFlow] of results.
     */
    @Serializable
    data class Data<T : Any>(
        val data: T,
        val seed: Long = Random.nextLong(),
    ) : NavigationResult<T>()

    /**
     * Represents a cancelled navigation result. Is emitted by a destination [NavigationResultExecutionScope] when it's destroyed.
     * This cancels suspending collection of results on the collector's side.
     */
    @Serializable
    data object ScopeDestroyed : NavigationResult<Nothing>()
}

/**
 * A typealias for [MutableStateFlow] that holds [NavigationResult] of generic type.
 */
typealias NavigationResultFlow<T> = MutableStateFlow<NavigationResult<T>>

/**
 * Creates an empty [MutableStateFlow] of [NavigationResult].
 *
 * @param T The type of the navigation result.
 * @return An empty [MutableStateFlow] of [NavigationResult].
 */
fun <T : Any> emptyNavigationResults(): MutableStateFlow<NavigationResult<T>> =
    MutableStateFlow(NavigationResult.Empty)

/**
 * Collects the results from the [MutableStateFlow] of [NavigationResult].
 *
 * @param T The type of the navigation result.
 * @return A [Flow] of the collected results.
 * @throws CancellationException if the navigation result is cancelled.
 */
inline fun <reified T : Any> MutableStateFlow<NavigationResult<T>>.collectResults(): Flow<T> = this
    .onEach { result ->
        if (result is NavigationResult.ScopeDestroyed) {
            throw CancellationException("NavigationResultExecutionScope for type ${T::class.simpleName} was canceled")
        }
    }
    .filterIsInstance<NavigationResult.Data<T>>()
    .map { it.data }

/**
 * Awaits a single result from the [MutableStateFlow] of [NavigationResult].
 *
 * @param T The type of the navigation result.
 * @return The awaited result.
 * @throws CancellationException if the navigation result is cancelled.
 */
suspend inline fun <reified T : Any> MutableStateFlow<NavigationResult<T>>.awaitResult(): T = this
    .collectResults()
    .first()
