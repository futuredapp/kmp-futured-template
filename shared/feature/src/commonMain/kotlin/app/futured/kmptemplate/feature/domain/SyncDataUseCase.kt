package app.futured.kmptemplate.feature.domain

import app.futured.kmptemplate.util.domain.UseCase
import kotlinx.coroutines.delay
import org.koin.core.annotation.Factory
import kotlin.time.Duration.Companion.seconds

@Factory
internal class SyncDataUseCase : UseCase<Unit, Nothing>() {

    override suspend fun build(args: Unit): Nothing {
        delay(10.seconds)
        error("Jokes on you, there's no data to be fetched in here.")
    }
}
