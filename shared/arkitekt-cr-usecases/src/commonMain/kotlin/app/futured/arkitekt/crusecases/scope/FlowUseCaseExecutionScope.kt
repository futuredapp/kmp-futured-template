package app.futured.arkitekt.crusecases.scope

import app.futured.arkitekt.crusecases.FlowUseCase
import app.futured.arkitekt.crusecases.error.UseCaseErrorHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlin.coroutines.cancellation.CancellationException

interface FlowUseCaseExecutionScope : CoroutineScopeOwner {

    /**
     * Map of [Job] objects used to hold and cancel existing run of any [FlowUseCase] instance.
     */
    val useCaseJobPool: MutableMap<FlowUseCase<*, *>, Job>

    fun <T : Any?> FlowUseCase<Unit, T>.execute(config: FlowUseCaseConfig.Builder<T, T>.() -> Unit) =
        execute(Unit, config)

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
    fun <ARGS, T : Any?, M : Any?> FlowUseCase<ARGS, T>.executeMapped(
        args: ARGS,
        config: FlowUseCaseConfig.Builder<T, M>.() -> Unit,
    ) {
        val flowUseCaseConfig = FlowUseCaseConfig.Builder<T, M>().run {
            config.invoke(this)
            return@run build()
        }

        if (flowUseCaseConfig.disposePrevious) {
            useCaseJobPool[this]?.cancel()
        }

        useCaseJobPool[this] = build(args)
            .flowOn(getWorkerDispatcher())
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
            .launchIn(useCaseScope)
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
    fun <ARGS, T : Any?> FlowUseCase<ARGS, T>.execute(
        args: ARGS,
        config: FlowUseCaseConfig.Builder<T, T>.() -> Unit,
    ) {
        val flowUseCaseConfig = FlowUseCaseConfig.Builder<T, T>().run {
            config.invoke(this)
            return@run build()
        }

        if (flowUseCaseConfig.disposePrevious) {
            useCaseJobPool[this]?.cancel()
        }

        useCaseJobPool[this] = build(args)
            .flowOn(getWorkerDispatcher())
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
            .launchIn(useCaseScope)
    }

    /**
     * Holds references to lambdas and some basic configuration
     * used to process results of Flow use case.
     * Use [FlowUseCaseConfig.Builder] to construct this object.
     */
    class FlowUseCaseConfig<T, M> private constructor(
        val onStart: () -> Unit,
        val onNext: (M) -> Unit,
        val onError: (Throwable) -> Unit,
        val onComplete: () -> Unit,
        val disposePrevious: Boolean,
        val onMap: ((T) -> M)? = null,
    ) {
        /**
         * Constructs references to lambdas and some basic configuration
         * used to process results of Flow use case.
         */
        class Builder<T, M> {
            private var onStart: (() -> Unit)? = null
            private var onNext: ((M) -> Unit)? = null
            private var onMap: ((T) -> M)? = null
            private var onError: ((Throwable) -> Unit)? = null
            private var onComplete: (() -> Unit)? = null
            private var disposePrevious = true

            /**
             * Set lambda that is called right before
             * internal Job of Flow is launched.
             * @param onStart Lambda called right before Flow Job is launched.
             */
            fun onStart(onStart: () -> Unit) {
                this.onStart = onStart
            }

            /**
             * Set lambda that is called when internal Flow emits new value
             * @param onNext Lambda called for every new emitted value
             */
            fun onNext(onNext: (M) -> Unit) {
                this.onNext = onNext
            }

            /**
             * Set lambda that is called when internal Flow emits new value
             * @param onNext Lambda called for every new emitted value
             */
            fun onMap(onMap: (T) -> M) {
                this.onMap = onMap
            }

            /**
             * Set lambda that is called when some exception on
             * internal Flow occurs
             * @param onError Lambda called when exception occurs
             */
            fun onError(onError: (Throwable) -> Unit) {
                this.onError = onError
            }

            /**
             * Set lambda that is called when internal Flow is completed
             * without errors
             * @param onComplete Lambda called when Flow is completed
             * without errors
             */
            fun onComplete(onComplete: () -> Unit) {
                this.onComplete = onComplete
            }

            /**
             * Set whether currently running Job of internal Flow
             * should be canceled when execute is called repeatedly.
             * @param disposePrevious True if currently running
             * Job of internal Flow should be canceled
             */
            fun disposePrevious(disposePrevious: Boolean) {
                this.disposePrevious = disposePrevious
            }

            fun build(): FlowUseCaseConfig<T, M> = FlowUseCaseConfig(
                onStart ?: { },
                onNext ?: { },
                onError ?: { throw it },
                onComplete ?: { },
                disposePrevious,
                onMap,
            )
        }
    }
}
