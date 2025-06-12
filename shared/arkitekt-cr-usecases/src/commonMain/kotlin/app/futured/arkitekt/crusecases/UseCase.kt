package app.futured.arkitekt.crusecases

import app.futured.arkitekt.crusecases.scope.SingleUseCaseExecutionScope

/**
 * Base Coroutine use case meant to use in [SingleUseCaseExecutionScope] implementations
 */
interface UseCase<ARGS, T> {

    /**
     * Suspend function which should contain business logic
     */
    suspend fun build(args: ARGS): T
}
