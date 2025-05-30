package app.futured.arkitekt.crusecases.scope

import app.futured.arkitekt.crusecases.UseCase
import app.futured.arkitekt.crusecases.error.UseCaseErrorHandler
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

interface SingleUseCaseExecutionScope : CoroutineScopeOwner {

    /**
     * Asynchronously executes use case and saves it's Deferred. By default, all previous
     * pending executions are canceled, this can be changed by the [config].
     * This version is used for use cases without initial arguments.
     *
     * @param config [UseCaseConfig] used to process results of internal
     * Coroutine and to set configuration options.
     */
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
        deferred = viewModelScope
            .async(context = getWorkerDispatcher(), start = CoroutineStart.LAZY) {
                build(args)
            }
            .also {
                viewModelScope.launch(Dispatchers.Main) {
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
    suspend fun <ARGS, T : Any?> UseCase<ARGS, T>.execute(
        args: ARGS,
        cancelPrevious: Boolean = true,
    ): Result<T> {
        if (cancelPrevious) {
            deferred?.cancel()
        }

        return try {
            val newDeferred = viewModelScope.async(getWorkerDispatcher(), CoroutineStart.LAZY) {
                build(args)
            }.also { deferred = it }

            Result.success(newDeferred.await())
        } catch (exception: CancellationException) {
            throw exception
        } catch (exception: Throwable) {
            Result.failure(exception)
        }
    }

    /**
     * Holds references to lambdas and some basic configuration
     * used to process results of Coroutine use case.
     * Use [UseCaseConfig.Builder] to construct this object.
     */
    class UseCaseConfig<T> private constructor(
        val onStart: () -> Unit,
        val onSuccess: (T) -> Unit,
        val onError: (Throwable) -> Unit,
        val disposePrevious: Boolean,
    ) {
        /**
         * Constructs references to lambdas and some basic configuration
         * used to process results of Coroutine use case.
         */
        class Builder<T> {
            private var onStart: (() -> Unit)? = null
            private var onSuccess: ((T) -> Unit)? = null
            private var onError: ((Throwable) -> Unit)? = null
            private var disposePrevious = true

            /**
             * Set lambda that is called right before
             * the internal Coroutine is created
             * @param onStart Lambda called right before Coroutine is
             * created
             */
            fun onStart(onStart: () -> Unit) {
                this.onStart = onStart
            }

            /**
             * Set lambda that is called when internal Coroutine
             * finished without exceptions
             * @param onSuccess Lambda called when Coroutine finished
             */
            fun onSuccess(onSuccess: (T) -> Unit) {
                this.onSuccess = onSuccess
            }

            /**
             * Set lambda that is called when exception on
             * internal Coroutine occurs
             * @param onError Lambda called when exception occurs
             */
            fun onError(onError: (Throwable) -> Unit) {
                this.onError = onError
            }

            /**
             * Set whether currently active Job of internal Coroutine
             * should be canceled when execute is called repeatedly.
             * Default value is true.
             * @param disposePrevious True if active Job of internal
             * Coroutine should be canceled. Default value is true.
             */
            fun disposePrevious(disposePrevious: Boolean) {
                this.disposePrevious = disposePrevious
            }

            fun build(): UseCaseConfig<T> = UseCaseConfig(
                onStart ?: { },
                onSuccess ?: { },
                onError ?: { throw it },
                disposePrevious,
            )
        }
    }
}
