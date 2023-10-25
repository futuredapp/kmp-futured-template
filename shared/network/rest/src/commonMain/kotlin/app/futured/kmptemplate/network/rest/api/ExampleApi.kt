package app.futured.kmptemplate.network.rest.api

import app.futured.kmptemplate.network.rest.result.NetworkResult
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface ExampleApi {

    @GET("people/{id}")
    suspend fun getPerson(@Path("id") personId: Int): NetworkResult<Person>
}

@Serializable
data class Person(
    @SerialName("name") val name: String? = null,
    @SerialName("homeworld") val homeworld: String? = null,
    @SerialName("gender") val gender: String? = null,
    @SerialName("url") val url: String? = null,
)
