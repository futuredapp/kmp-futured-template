package app.futured.kmptemplate.feature.ui.secondScreen

import app.futured.kmptemplate.feature.model.ui.AvatarStyle
import kotlinx.coroutines.flow.StateFlow

interface SecondScreen {

    val viewState: StateFlow<SecondViewState>
    val actions: Actions

    interface Actions {
        fun onCancel()
        fun onGenerate()
        fun onRetry()
        fun onStyle(style: AvatarStyle)
    }
}
