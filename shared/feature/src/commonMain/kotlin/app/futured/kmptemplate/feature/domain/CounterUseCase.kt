package app.futured.kmptemplate.feature.domain

import app.futured.kmptemplate.util.domain.FlowUseCase
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import org.koin.core.annotation.Factory
import kotlin.time.Duration

@Factory
internal class CounterUseCase : FlowUseCase<CounterUseCaseArgs, Long>() {

    override fun build(args: CounterUseCaseArgs): Flow<Long> = flow {
        var counter = 0L
        while (currentCoroutineContext().isActive) {
            emit(counter++)
            delay(args.interval)
        }
    }
}

internal data class CounterUseCaseArgs(val interval: Duration)
