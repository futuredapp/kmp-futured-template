package app.futured.kmptemplate.feature.ui.firstScreen

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow

object FirstScreenPreviews {
    fun screen(viewState: FirstViewState = FirstViewState.mock()): FirstScreen = object : FirstScreen {
        override val viewState: StateFlow<FirstViewState> = MutableStateFlow(viewState)
        override val actions: FirstScreen.Actions = FirstScreen.Actions.noOpActions()
        override val events: Flow<FirstUiEvent> = emptyFlow()
    }
}
