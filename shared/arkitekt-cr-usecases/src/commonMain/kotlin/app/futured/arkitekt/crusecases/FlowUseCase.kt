package app.futured.arkitekt.crusecases

import app.futured.arkitekt.crusecases.scope.FlowUseCaseExecutionScope
import kotlinx.coroutines.flow.Flow

/**
 * Base [Flow] use case meant to use in [FlowUseCaseExecutionScope] implementations
 */
interface FlowUseCase<ARGS, T> {

    /**
     * Function which builds Flow instance based on given arguments
     * @param args initial use case arguments
     */
    fun build(args: ARGS): Flow<T>
}
