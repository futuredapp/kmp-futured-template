package app.futured.kmptemplate.feature.ui.first

import app.futured.arkitekt.decompose.presentation.UiEvent

sealed class FirstUiEvent : UiEvent<FirstViewState> {
    data class ShowToast(val text: String) : FirstUiEvent()
}
