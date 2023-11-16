package app.futured.kmptemplate.feature.ui.first

import app.futured.kmptemplate.util.arch.OutputEvent
import app.futured.kmptemplate.util.arch.UiEvent

sealed class FirstEvent : OutputEvent<FirstViewState> {
    data object NavigateBack : FirstEvent()
    data object NavigateNext : FirstEvent()
}

sealed class FirstUiEvent : UiEvent<FirstViewState> {
    data class ShowToast(val text: String) : FirstUiEvent()
}
