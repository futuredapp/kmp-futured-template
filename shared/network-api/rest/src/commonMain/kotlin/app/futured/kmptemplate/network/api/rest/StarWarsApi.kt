package app.futured.kmptemplate.network.api.rest

import app.futured.kmptemplate.network.api.rest.dto.Person
import app.futured.kmptemplate.network.api.rest.result.NetworkResult
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path

interface StarWarsApi {

    @GET("people/{id}")
    suspend fun getPerson(
        @Path("id") personId: Int,
    ): NetworkResult<Person>
}
