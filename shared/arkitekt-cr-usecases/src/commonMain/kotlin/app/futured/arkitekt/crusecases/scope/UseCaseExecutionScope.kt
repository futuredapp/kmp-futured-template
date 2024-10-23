package app.futured.arkitekt.crusecases.scope

import app.futured.arkitekt.crusecases.error.UseCaseErrorHandler
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Combines [SingleUseCaseExecutionScope] and [FlowUseCaseExecutionScope] under one interface.
 * The [UseCaseExecutionScope] would be usually implemented in class that wishes to execute UseCases.
 */
interface UseCaseExecutionScope : SingleUseCaseExecutionScope, FlowUseCaseExecutionScope {

    /**
     * Launch suspend [block] in [viewModelScope].
     *
     * Encapsulates this call with try catch block and when an exception is thrown
     * then it is logged in [UseCaseErrorHandler.globalOnErrorLogger] and handled by [defaultErrorHandler].
     *
     * If exception is [CancellationException] then [defaultErrorHandler] is not called and
     * [UseCaseErrorHandler.globalOnErrorLogger] is called only if the root cause of this exception is not
     * [CancellationException] (e.g. when [Result.getOrCancel] is used).
     */
    @Suppress("TooGenericExceptionCaught")
    fun launchWithHandler(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            try {
                block()
            } catch (exception: CancellationException) {
                val rootCause = exception.cause
                if (rootCause != null && rootCause !is CancellationException) {
                    UseCaseErrorHandler.globalOnErrorLogger(exception)
                }
            } catch (exception: Throwable) {
                UseCaseErrorHandler.globalOnErrorLogger(exception)
                defaultErrorHandler(exception)
            }
        }
    }

    /**
     * This method is called when coroutine launched with [launchWithHandler] throws an exception and
     * this exception isn't [CancellationException]. By default, it rethrows this exception.
     */
    fun defaultErrorHandler(exception: Throwable) {
        throw exception
    }
}

fun <T> Result<T>.getOrCancel(): T {
    return this.getOrElse { throw CancellationException("Result was not Success") }
}
