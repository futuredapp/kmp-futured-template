package app.futured.kmptemplate.feature.ui.firstScreen

import app.futured.arkitekt.decompose.presentation.UiEvent

sealed class FirstUiEvent : UiEvent {
    data object ShowToast : FirstUiEvent()
}
