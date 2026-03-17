package app.futured.kmptemplate.feature.domain

import app.futured.arkitekt.crusecases.UseCase
import app.futured.kmptemplate.network.rest.api.StarWarsApi
import app.futured.kmptemplate.network.rest.dto.Person
import app.futured.kmptemplate.network.rest.result.getOrThrow
import org.koin.core.annotation.Factory
import kotlin.random.Random

/**
 * Fetches a random person from StarWars API.
 */
internal fun interface FetchDataUseCase : UseCase<Unit, Person>

@Factory
internal class FetchDataUseCaseImpl(private val starWarsApi: StarWarsApi) : FetchDataUseCase {

    override suspend fun build(args: Unit): Person =
        starWarsApi.getPerson(Random.nextInt(until = 100)).getOrThrow()
}
