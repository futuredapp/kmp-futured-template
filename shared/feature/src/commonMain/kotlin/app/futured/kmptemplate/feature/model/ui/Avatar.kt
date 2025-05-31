package app.futured.kmptemplate.feature.model.ui

import kotlinx.serialization.Serializable

@Serializable
data class Avatar(
    val id: Int,
    val preview: String,
    val image: String,
    val status: AvatarStatus,
    val style: AvatarStyle,
)

enum class AvatarStatus {
    InProgress,
    Done,
    Failed,
}
