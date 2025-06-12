package app.futured.kmptemplate.feature.domain

import app.futured.arkitekt.crusecases.FlowUseCase
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import org.koin.core.annotation.Factory
import kotlin.time.Duration

/**
 * Emits increment in intervals specified by [Args.interval].
 */
internal interface CounterUseCase : FlowUseCase<CounterUseCase.Args, Long> {
    data class Args(val interval: Duration)
}

@Factory
internal class CounterUseCaseImpl : CounterUseCase {

    override fun build(args: CounterUseCase.Args): Flow<Long> = flow {
        var counter = 0L
        while (currentCoroutineContext().isActive) {
            emit(counter++)
            delay(args.interval)
        }
    }
}
