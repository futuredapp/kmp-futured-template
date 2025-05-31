package app.futured.kmptemplate.network.rest.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AvatarStyleResponse(
    @SerialName("ID") val id: Long,
    @SerialName("Name") val name: String,
    @SerialName("Preview") val preview: String,
)
