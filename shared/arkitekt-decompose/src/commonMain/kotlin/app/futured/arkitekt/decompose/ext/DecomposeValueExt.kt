package app.futured.arkitekt.decompose.ext

import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

/**
 * Converts this Decompose [Value] to Kotlin [Flow].
 *
 * @param T The type of the value.
 * @return A [Flow] emitting the values of the Decompose [Value].
 */
fun <T : Any> Value<T>.asFlow(): Flow<T> = callbackFlow {
    val cancellation = subscribe { value ->
        trySendBlocking(value)
    }

    awaitClose {
        cancellation.cancel()
    }
}

/**
 * Converts this Decompose [Value] to Kotlin [StateFlow].
 *
 * @param T The type of the value.
 * @param coroutineScope The [CoroutineScope] in which the [StateFlow] is active.
 * @param started The [SharingStarted] strategy for the [StateFlow].
 * @param onStarted A lambda to be executed before the flow start to be collected.
 * @return A [StateFlow] emitting the values of the [Value].
 */
fun <T : Any> Value<T>.asStateFlow(
    coroutineScope: CoroutineScope,
    started: SharingStarted = SharingStarted.Lazily,
    onStarted: suspend FlowCollector<T>.() -> Unit = {},
): StateFlow<T> = asFlow()
    .onStart(onStarted)
    .stateIn(
        scope = coroutineScope,
        started = started,
        initialValue = value,
    )
