package app.futured.kmptemplate.util.ext

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

/**
 * Converts this Decompose [Value] to Kotlin [Flow].
 */
fun <T : Any> Value<T>.asFlow(): Flow<T> = callbackFlow {
    val cancellation = observe { value ->
        trySendBlocking(value)
    }

    awaitClose {
        cancellation.cancel()
    }
}

/**
 * Mutates provided [mutableValue] using [transform] function.
 */
fun <T : Any> update(mutableValue: MutableValue<T>, transform: T.() -> T) {
    mutableValue.value = transform(mutableValue.value)
}
