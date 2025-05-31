package app.futured.kmptemplate.feature.ui.welcomeScreen

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface WelcomeScreen {
    val viewState: StateFlow<WelcomeViewState>
    val events: Flow<WelcomeUIEvent>
    val actions: Actions

    interface Actions {
        fun onCameraPermissionGranted()
        fun onCameraPermissionDenied()
    }
}
