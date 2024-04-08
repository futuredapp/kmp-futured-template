package app.futured.kmptemplate.util.domain

import app.futured.kmptemplate.util.domain.scope.SingleUseCaseExecutionScope
import kotlinx.coroutines.Deferred

/**
 * Base Coroutine use case meant to use in [SingleUseCaseExecutionScope] implementations
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
