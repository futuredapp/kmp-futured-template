package app.futured.kmptemplate.feature.ui.second

import app.futured.kmptemplate.util.arch.OutputEvent

sealed class SecondEvent : OutputEvent<SecondViewState> {
    data object NavigateBack : SecondEvent()
    data object NavigateNext : SecondEvent()
}
