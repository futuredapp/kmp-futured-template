package app.futured.kmptemplate.feature.ui.first

import app.futured.kmptemplate.util.arch.UiEvent

sealed class FirstUiEvent : UiEvent<FirstViewState> {
    data class ShowToast(val text: String) : FirstUiEvent()
}
