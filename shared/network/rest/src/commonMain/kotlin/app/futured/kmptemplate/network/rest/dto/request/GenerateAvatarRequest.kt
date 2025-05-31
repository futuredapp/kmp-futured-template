package app.futured.kmptemplate.network.rest.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenerateAvatarRequest(
    @SerialName("FaceImageUrl") val faceImageUrl: String,
    @SerialName("PoseImageUrl") val poseImageUrl: String,
    @SerialName("StyleId") val styleId: Long,
)

