package app.futured.kmptemplate.util.domain.usecases

import app.futured.kmptemplate.util.domain.FlowUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.onEach

class TestFlowUseCase : FlowUseCase<TestFlowUseCase.Data, Int>() {

    data class Data(
        val listToEmit: List<Int>,
        val delayBetweenEmits: Long,
    )

    override fun build(args: Data): Flow<Int> =
        args.listToEmit.asFlow().onEach { delay(args.delayBetweenEmits) }
}
