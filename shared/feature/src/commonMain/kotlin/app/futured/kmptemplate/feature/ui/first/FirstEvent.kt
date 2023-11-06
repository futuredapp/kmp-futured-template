package app.futured.kmptemplate.feature.ui.first

import app.futured.kmptemplate.util.arch.OutputEvent

sealed class FirstEvent : OutputEvent<FirstViewState> {
    data object NavigateBack : FirstEvent()
    data object NavigateNext : FirstEvent()
}
