package app.futured.kmptemplate.feature.ui.secondScreen

import app.futured.kmptemplate.feature.model.ui.AvatarStyle
import app.futured.kmptemplate.feature.tool.UiError
import app.futured.kmptemplate.feature.ui.dialog.UiDialog


data class SecondViewState(
    val styles: List<AvatarStyle> = emptyList(),
    val isLoading: Boolean = true,
    val error: UiError? = null,
    val selectedStyle: AvatarStyle? = null,
    val isGenerating: Boolean = false,
    val uiDialog: UiDialog? = null,
)
