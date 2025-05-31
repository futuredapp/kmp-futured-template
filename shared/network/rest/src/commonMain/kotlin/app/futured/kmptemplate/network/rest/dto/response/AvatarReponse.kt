package app.futured.kmptemplate.network.rest.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AvatarResponse(
    @SerialName("Id") val id: Int,
    @SerialName("Preview") val preview: String,
    @SerialName("Image") val image: String,
    @SerialName("Status") val status: String,
    @SerialName("StyleId") val styleId: Long,
)

enum class AvatarStatus(val value: String) {
    Starting("starting"),
    Processing("processing"),
    Done("succeeded"),
    Failed("failed"),
    Canceled("canceled"),
}

