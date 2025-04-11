package app.futured.kmptemplate.network.api.rest.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Person(
    @SerialName("name") val name: String? = null,
    @SerialName("homeworld") val homeworld: String? = null,
    @SerialName("gender") val gender: String? = null,
    @SerialName("url") val url: String? = null,
)
