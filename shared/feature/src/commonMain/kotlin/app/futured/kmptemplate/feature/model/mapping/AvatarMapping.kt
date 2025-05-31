package app.futured.kmptemplate.feature.model.mapping

import app.futured.kmptemplate.feature.model.ui.Avatar
import app.futured.kmptemplate.feature.model.ui.AvatarStatus
import app.futured.kmptemplate.feature.model.ui.AvatarStyle
import app.futured.kmptemplate.feature.tool.gsToHttpGcloudUrl
import app.futured.kmptemplate.network.rest.dto.response.AvatarResponse
import app.futured.kmptemplate.network.rest.dto.response.AvatarStyleResponse
import app.futured.kmptemplate.network.rest.dto.response.AvatarStatus as NetworkAvatarStatus

fun AvatarResponse.toUiModel(avatarStyle: AvatarStyle): Avatar {
    return Avatar(
        id = id,
        preview = preview.gsToHttpGcloudUrl(),
        image = image.gsToHttpGcloudUrl(),
        status = NetworkAvatarStatus.entries.first { it.value==status }.toUiEnum(),
        style = avatarStyle,
    )
}

fun NetworkAvatarStatus.toUiEnum(): AvatarStatus {
    return when (this) {

        NetworkAvatarStatus.Starting -> AvatarStatus.InProgress
        NetworkAvatarStatus.Processing -> AvatarStatus.InProgress
        NetworkAvatarStatus.Done -> AvatarStatus.Done
        NetworkAvatarStatus.Failed -> AvatarStatus.Failed
        NetworkAvatarStatus.Canceled -> AvatarStatus.Failed
    }
}

fun AvatarStyleResponse.toUiModel(): AvatarStyle {
    return AvatarStyle(
        id = id,
        name = name,
        preview = preview,
    )
}
