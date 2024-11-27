package app.futured.kmptemplate.feature.domain

import app.futured.arkitekt.crusecases.UseCase
import kotlinx.coroutines.delay
import org.koin.core.annotation.Factory
import kotlin.time.Duration.Companion.seconds

@Factory
internal class SyncDataUseCase : UseCase<Unit, Nothing>() {

    override suspend fun build(args: Unit): Nothing {
        delay(2.seconds)
        error("This is mock implementation of UseCase and this error only serves demonstration purpose. No actual data is being fetched.")
    }
}
