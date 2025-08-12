package app.futured.kmptemplate.feature.domain

import app.futured.arkitekt.crusecases.FlowUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.koin.core.annotation.Factory

@Factory
internal class TimeStampUseCase() : FlowUseCase<Unit, Instant>() {
    override fun build(args: Unit): Flow<Instant> = flow {
        for (i in 0..1000) {
            emit(Clock.System.now())
            delay(1000)
        }
    }
}
