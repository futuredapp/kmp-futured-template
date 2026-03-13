package app.futured.arkitekt.crusecases.scope

import app.futured.arkitekt.crusecases.FlowUseCase
import app.futured.arkitekt.crusecases.UseCase
import app.futured.arkitekt.crusecases.error.UseCaseErrorHandler
import app.futured.arkitekt.crusecases.error.UseCaseErrorHandler.globalOnErrorLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

/**
 * This interface gives your class ability to execute [UseCase] and [FlowUseCase] Coroutine use cases.
 * You may find handy to implement this interface in custom Presenters, ViewHolders etc.
 * It is your responsibility to cancel [useCaseScope] when all running tasks should be stopped.
 */
interface CoroutineScopeOwner {
    /**
     * [CoroutineScope] scope used to execute coroutine based use cases.
     * It is your responsibility to cancel it when all running
     * tasks should be stopped
     */
    val useCaseScope: CoroutineScope

    /**
     * Map of [Job] objects used to hold and cancel existing run of any [FlowUseCase] instance.
     */
    val useCaseJobPool: MutableMap<FlowUseCase<*, *>, Job>

    /**
     * Map of [Deferred] objects used to hold and cancel existing run of any [UseCase] instance.
     */
    val useCaseDeferredPool: MutableMap<UseCase<*, *>, Deferred<*>>

    /**
     * Provides Dispatcher for background tasks. This may be overridden for testing purposes.
     */
    fun getWorkerDispatcher() = Dispatchers.Default

    /**
     * Launch suspend [block] in [useCaseScope].
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
        useCaseScope.launch {
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
    fun defaultErrorHandler(exception: Throwable): Unit = throw exception
}
