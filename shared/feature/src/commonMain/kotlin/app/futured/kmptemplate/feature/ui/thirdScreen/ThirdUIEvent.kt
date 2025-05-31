package app.futured.kmptemplate.feature.ui.thirdScreen

import app.futured.arkitekt.decompose.presentation.UiEvent

sealed class ThirdUIEvent : UiEvent {
    data object Share : ThirdUIEvent()
}
