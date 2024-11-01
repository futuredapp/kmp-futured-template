package app.futured.kmptemplate.util.domain.usecases

import app.futured.kmptemplate.util.domain.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TestFailureFlowUseCase : FlowUseCase<Throwable, Unit>() {

    override fun build(args: Throwable): Flow<Unit> = flow {
        throw args
    }
}
