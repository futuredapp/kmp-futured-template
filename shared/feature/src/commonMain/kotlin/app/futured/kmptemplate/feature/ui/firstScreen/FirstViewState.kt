package app.futured.kmptemplate.feature.ui.firstScreen

import app.futured.kmptemplate.feature.model.ui.Avatar
import app.futured.kmptemplate.feature.tool.UiError
import app.futured.kmptemplate.feature.ui.dialog.UiDialog

data class FirstViewState(
    val avatars: List<Avatar> = emptyList(),
    val selectedAvatar: String? = null,
    val isLoading: Boolean = true,
    val error: UiError? = null,
    val uiDialog: UiDialog? = null,
    val photo: String? = null,
    val isPhotoLoading: Boolean = false,
    )
