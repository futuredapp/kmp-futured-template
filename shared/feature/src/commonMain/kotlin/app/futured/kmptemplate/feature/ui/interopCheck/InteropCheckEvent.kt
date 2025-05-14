package app.futured.kmptemplate.feature.ui.interopCheck

import app.futured.arkitekt.decompose.presentation.UiEvent

sealed class InteropCheckEvent : UiEvent {
    data class LaunchWebBrowser(val url: String) : InteropCheckEvent()
}
