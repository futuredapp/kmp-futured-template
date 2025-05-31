package app.futured.kmptemplate.feature.ui.thirdScreen

import app.futured.kmptemplate.feature.model.ui.Avatar
import app.futured.kmptemplate.feature.ui.dialog.UiDialog

data class ThirdViewState(
    val avatar: Avatar,
    val shareAvatarImageLoading: Boolean = false,
    val uiDialog: UiDialog? = null,
)
