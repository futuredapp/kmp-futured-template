package app.futured.arkitekt.crusecases

import app.futured.arkitekt.crusecases.error.UseCaseErrorHandler
import app.futured.arkitekt.crusecases.scope.CoroutineScopeOwner
import app.futured.arkitekt.crusecases.scope.UseCaseConfig
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

/**
 * Base Coroutine use case meant to use in [CoroutineScopeOwner] implementations
 */
abstract class UseCase<ARGS, T> {

    /**
     *  [Deferred] used to hold and cancel existing run of this use case
     */
    var deferred: Deferred<T>? = null

    /**
     * Suspend function which should contain business logic
     */
    abstract suspend fun build(args: ARGS): T
}

/**
 * Asynchronously executes use case and saves it's Deferred. By default, all previous
 * pending executions are canceled, this can be changed by the [config].
 * This version is used for use cases without initial arguments.
 *
 * @param config [UseCaseConfig] used to process results of internal
 * Coroutine and to set configuration options.
 */
context(coroutineScopeOwner: CoroutineScopeOwner)
fun <T : Any?> UseCase<Unit, T>.execute(config: UseCaseConfig.Builder<T>.() -> Unit) =
    execute(Unit, config)

/**
 * Asynchronously executes use case and saves it's Deferred. By default, all previous
 * pending executions are canceled, this can be changed by the [config].
 * This version gets initial arguments by [args].
 *
 * In case that an error is thrown during the execution of [UseCase] then [UseCaseErrorHandler.globalOnErrorLogger]
 * is called with the error as an argument.
 *
 * @param args Arguments used for initial use case initialization.
 * @param config [UseCaseConfig] used to process results of internal
 * Coroutine and to set configuration options.
 */
context(coroutineScopeOwner: CoroutineScopeOwner)
fun <ARGS, T : Any?> UseCase<ARGS, T>.execute(
    args: ARGS,
    config: UseCaseConfig.Builder<T>.() -> Unit,
) {
    val useCaseConfig = UseCaseConfig.Builder<T>().run {
        config.invoke(this)
        return@run build()
    }
    if (useCaseConfig.disposePrevious) {
        deferred?.cancel()
    }

    useCaseConfig.onStart()
    deferred = coroutineScopeOwner.viewModelScope
        .async(context = coroutineScopeOwner.getWorkerDispatcher(), start = CoroutineStart.LAZY) {
            build(args)
        }
        .also {
            coroutineScopeOwner.viewModelScope.launch(Dispatchers.Main) {
                try {
                    useCaseConfig.onSuccess(it.await())
                } catch (cancellation: CancellationException) {
                    // do nothing - this is normal way of suspend function interruption
                } catch (error: Throwable) {
                    UseCaseErrorHandler.globalOnErrorLogger(error)
                    useCaseConfig.onError(error)
                }
            }
        }
}

/**
 * Synchronously executes use case and saves it's Deferred. By default all previous
 * pending executions are canceled, this can be changed by the [cancelPrevious].
 * This version gets initial arguments by [args].
 *
 * [UseCaseErrorHandler.globalOnErrorLogger] is not used in this version of the execute
 * method since it is recommended to call all execute methods with [Result] return type
 * from [UseCaseExecutionScope.launchWithHandler] method,
 * where [UseCaseErrorHandler.globalOnErrorLogger] is used.
 *
 * @param args Arguments used for initial use case initialization.
 * @return [Result] that encapsulates either a successful result with [Success] or a failed result with [Error]
 */
@Suppress("TooGenericExceptionCaught")
context(coroutineScopeOwner: CoroutineScopeOwner)
suspend fun <ARGS, T : Any?> UseCase<ARGS, T>.execute(
    args: ARGS,
    cancelPrevious: Boolean = true,
): Result<T> {
    if (cancelPrevious) {
        deferred?.cancel()
    }

    return try {
        val newDeferred = coroutineScopeOwner.viewModelScope.async(coroutineScopeOwner.getWorkerDispatcher(), CoroutineStart.LAZY) {
            build(args)
        }.also { deferred = it }

        Result.success(newDeferred.await())
    } catch (exception: CancellationException) {
        throw exception
    } catch (exception: Throwable) {
        Result.failure(exception)
    }
}
