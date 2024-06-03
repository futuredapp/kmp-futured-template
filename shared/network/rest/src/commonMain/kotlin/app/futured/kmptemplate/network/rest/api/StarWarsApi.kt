package app.futured.kmptemplate.network.rest.api

import app.futured.kmptemplate.network.rest.dto.Person
import app.futured.kmptemplate.network.rest.result.NetworkResult
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path

interface StarWarsApi {

    @GET("people/{id}")
    suspend fun getPerson(
        @Path("id") personId: Int,
    ): NetworkResult<Person>
}
