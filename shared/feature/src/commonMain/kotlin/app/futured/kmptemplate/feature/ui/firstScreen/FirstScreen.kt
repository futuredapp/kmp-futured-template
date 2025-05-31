package app.futured.kmptemplate.feature.ui.firstScreen

import app.futured.kmptemplate.feature.model.ui.Avatar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface FirstScreen {
    val viewState: StateFlow<FirstViewState>
    val actions: Actions
    val events: Flow<FirstUiEvent>

    interface Actions {
        fun onCreateNewAvatar()
        fun onAvatar(avatar: Avatar)
        fun onCameraPermissionDenied()
        fun onRetry()
        fun onRetakePhoto(imageName: String, imageData: ByteArray)
    }
}
