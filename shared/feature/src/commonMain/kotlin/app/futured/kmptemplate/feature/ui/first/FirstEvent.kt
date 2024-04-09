package app.futured.kmptemplate.feature.ui.first

import app.futured.kmptemplate.util.arch.Event

sealed class FirstEvent : Event<FirstViewState> {
    data class ShowToast(val text: String) : FirstEvent()
}
