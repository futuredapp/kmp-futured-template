package app.futured.kmptemplate.feature.domain

import app.futured.arkitekt.crusecases.FlowUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory
import kotlin.time.Clock
import kotlin.time.Instant

internal fun interface TimeStampUseCase : FlowUseCase<Unit, Instant>

@Factory
internal class TimeStampUseCaseImpl : TimeStampUseCase {
    override fun build(args: Unit): Flow<Instant> = flow {
        for (i in 0..1000) {
            emit(Clock.System.now())
            delay(1000)
        }
    }
}
