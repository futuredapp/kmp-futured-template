package app.futured.kmptemplate.feature_v3.ui.firstScreen

import app.futured.arkitekt.decompose.presentation.UiEvent

sealed class FirstUiEvent : UiEvent<FirstViewState> {
    data class ShowToast(val text: String) : FirstUiEvent()
}
