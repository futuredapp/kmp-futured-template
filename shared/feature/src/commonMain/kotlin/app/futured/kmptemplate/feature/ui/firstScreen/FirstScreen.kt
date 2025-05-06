package app.futured.kmptemplate.feature.ui.firstScreen

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface FirstScreen {
    val viewState: StateFlow<FirstViewState>
    val actions: Actions
    val events: Flow<FirstUiEvent>

    interface Actions {
        fun onNext()
        fun switchToComposeMultiplatform()
        fun switchToNative()
    }
}
