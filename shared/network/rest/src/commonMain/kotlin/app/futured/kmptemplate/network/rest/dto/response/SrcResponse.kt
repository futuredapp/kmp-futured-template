package app.futured.kmptemplate.network.rest.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SrcResponse(
    @SerialName("Data") val data: List<String>,
)
