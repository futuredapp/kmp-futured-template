package app.futured.kmptemplate.feature.ui.welcomeScreen

import app.futured.arkitekt.decompose.presentation.UiEvent


sealed class WelcomeUIEvent : UiEvent {
    data object OpenSystemSettings : WelcomeUIEvent()
}
