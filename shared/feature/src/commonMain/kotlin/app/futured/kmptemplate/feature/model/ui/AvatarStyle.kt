package app.futured.kmptemplate.feature.model.ui

import kotlinx.serialization.Serializable

@Serializable
data class AvatarStyle(
    val id: Long,
    val name: String,
    val preview: String,
)
