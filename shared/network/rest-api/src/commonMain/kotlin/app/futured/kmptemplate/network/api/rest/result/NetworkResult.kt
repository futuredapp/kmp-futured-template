@file:OptIn(ExperimentalContracts::class)

package app.futured.kmptemplate.network.api.rest.result

import app.futured.kmptemplate.network.api.rest.result.NetworkResult.Failure
import app.futured.kmptemplate.network.api.rest.result.NetworkResult.Success
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Wrapper class with either [Success] or [Failure] state.
 * The class is used as as a result of network operations.
 */
sealed class NetworkResult<out T> {

    /**
     * The success result with [data] as the response of the operation.
     */
    data class Success<T>(val data: T) : NetworkResult<T>()

    /**
     * The failed result with [error] as the failure cause of the operation.
     */
    data class Failure(val error: NetworkError) : NetworkResult<Nothing>()

    companion object {
        fun <T> success(data: T) = Success(data)
        fun error(error: NetworkError) = Failure(error)
    }
}

/**
 * Returns the encapsulated value if this instance represents [NetworkResult.Success] or
 * throws the encapsulated [Throwable] exception if it is [NetworkResult.Failure].
 */
inline fun <reified T> NetworkResult<T>.getOrThrow(): T = when (this) {
    is Success -> data
    is Failure -> throw error
}

/**
 * Returns the result of [onSuccess] for the encapsulated value if this instance represents [NetworkResult.Success]
 * or the result of [onFailure] function for the encapsulated [ApiException] error if it is [NetworkResult.Failure].
 *
 * Note, that this function rethrows any [Throwable] exception thrown by [onSuccess] or by [onFailure] function.
 */
inline fun <reified T> NetworkResult<T>.fold(
    onSuccess: (T) -> Unit,
    onFailure: (NetworkError) -> Unit,
) {
    contract {
        callsInPlace(onSuccess, InvocationKind.AT_MOST_ONCE)
        callsInPlace(onFailure, InvocationKind.AT_MOST_ONCE)
    }
    return when (this) {
        is Success -> onSuccess(data)
        is Failure -> onFailure(error)
    }
}

/**
 * Returns the encapsulated result of the given [transform] function applied to the encapsulated value
 * if this instance represents [NetworkResult.Success] or the
 * original encapsulated [Throwable] exception if it is [NetworkResult.Failure].
 *
 * Note, that this function rethrows any [Throwable] exception thrown by [transform] function.
 */
inline fun <R, T> NetworkResult<T>.map(transform: (value: T) -> R): NetworkResult<R> {
    contract {
        callsInPlace(transform, InvocationKind.AT_MOST_ONCE)
    }
    return when (this) {
        is Success -> Success(transform(data))
        is Failure -> this
    }
}
