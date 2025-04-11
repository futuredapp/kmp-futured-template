package app.futured.kmptemplate.feature.domain

import app.futured.arkitekt.crusecases.UseCase
import app.futured.kmptemplate.network.api.rest.StarWarsApi
import app.futured.kmptemplate.network.api.rest.dto.Person
import app.futured.kmptemplate.network.api.rest.result.getOrThrow
import org.koin.core.annotation.Factory
import kotlin.random.Random

@Factory
internal class SyncDataUseCase(private val starWarsApi: StarWarsApi) : UseCase<Unit, Person>() {

    override suspend fun build(args: Unit): Person = starWarsApi.getPerson(Random.nextInt(until = 100)).getOrThrow()
}
