package app.futured.arkitekt.crusecases

import app.futured.arkitekt.crusecases.error.UseCaseErrorHandler
import app.futured.arkitekt.crusecases.scope.CoroutineScopeOwner
import app.futured.arkitekt.crusecases.scope.FlowUseCaseConfig
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlin.coroutines.cancellation.CancellationException

/**
 * Base [Flow] use case meant to use in [CoroutineScopeOwner] implementations
 */
abstract class FlowUseCase<ARGS, T> {

    /**
     *  [Job] used to hold and cancel existing run of this use case
     */
    var job: Job? = null

    /**
     * Function which builds Flow instance based on given arguments
     * @param args initial use case arguments
     */
    abstract fun build(args: ARGS): Flow<T>
}

/**
 * Asynchronously executes use case and consumes data from flow on UI thread.
 * By default all previous pending executions are canceled, this can be changed
 * by [config]. When suspend function in use case finishes, onComplete is called
 * on UI thread. This version is gets initial arguments by [args].
 *
 * In case that an error is thrown during the execution of [FlowUseCase] then
 * [UseCaseErrorHandler.globalOnErrorLogger] is called with the error as an argument.
 *
 * @param args Arguments used for initial use case initialization.
 * @param config [FlowUseCaseConfig] used to process results of internal
 * Flow and to set configuration options.
 **/
context(coroutineScopeOwner: CoroutineScopeOwner)
fun <ARGS, T : Any?> FlowUseCase<ARGS, T>.execute(
    args: ARGS,
    config: FlowUseCaseConfig.Builder<T, T>.() -> Unit,
) {
    val flowUseCaseConfig = FlowUseCaseConfig.Builder<T, T>().run {
        config.invoke(this)
        return@run build()
    }

    if (flowUseCaseConfig.disposePrevious) {
        job?.cancel()
    }

    job = build(args)
        .flowOn(coroutineScopeOwner.getWorkerDispatcher())
        .onStart { flowUseCaseConfig.onStart() }
        .onEach { flowUseCaseConfig.onNext(it) }
        .onCompletion { error ->
            when {
                error is CancellationException -> {
                    // ignore this exception
                }

                error != null -> {
                    UseCaseErrorHandler.globalOnErrorLogger(error)
                    flowUseCaseConfig.onError(error)
                }

                else -> flowUseCaseConfig.onComplete()
            }
        }
        .catch { /* handled in onCompletion */ }
        .launchIn(coroutineScopeOwner.viewModelScope)
}

context(coroutineScopeOwner: CoroutineScopeOwner)
fun <T : Any?> FlowUseCase<Unit, T>.execute(config: FlowUseCaseConfig.Builder<T, T>.() -> Unit) =
    execute(Unit, config)

context(coroutineScopeOwner: CoroutineScopeOwner)
fun <T : Any?, M : Any?> FlowUseCase<Unit, T>.executeMapped(config: FlowUseCaseConfig.Builder<T, M>.() -> Unit) =
    executeMapped(Unit, config)

/**
 * Asynchronously executes use case and consumes data from flow on UI thread.
 * By default all previous pending executions are canceled, this can be changed
 * by [config]. When suspend function in use case finishes, onComplete is called
 * on UI thread. This version is gets initial arguments by [args].
 *
 * In case that an error is thrown during the execution of [FlowUseCase] then
 * [UseCaseErrorHandler.globalOnErrorLogger] is called with the error as an argument.
 *
 * @param args Arguments used for initial use case initialization.
 * @param config [FlowUseCaseConfig] used to process results of internal
 * Flow and to set configuration options.
 **/
context(coroutineScopeOwner: CoroutineScopeOwner)
fun <ARGS, T : Any?, M : Any?> FlowUseCase<ARGS, T>.executeMapped(
    args: ARGS,
    config: FlowUseCaseConfig.Builder<T, M>.() -> Unit,
) {
    val flowUseCaseConfig = FlowUseCaseConfig.Builder<T, M>().run {
        config.invoke(this)
        return@run build()
    }

    if (flowUseCaseConfig.disposePrevious) {
        job?.cancel()
    }

    job = build(args)
        .flowOn(coroutineScopeOwner.getWorkerDispatcher())
        .onStart { flowUseCaseConfig.onStart() }
        .mapNotNull { flowUseCaseConfig.onMap?.invoke(it) }
        .onEach { flowUseCaseConfig.onNext(it) }
        .onCompletion { error ->
            when {
                error is CancellationException -> {
                    // ignore this exception
                }

                error != null -> {
                    UseCaseErrorHandler.globalOnErrorLogger(error)
                    flowUseCaseConfig.onError(error)
                }

                else -> flowUseCaseConfig.onComplete()
            }
        }
        .catch { /* handled in onCompletion */ }
        .launchIn(coroutineScopeOwner.viewModelScope)
}
