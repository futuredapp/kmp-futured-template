package app.futured.kmptemplate.feature.ui.second

import app.futured.kmptemplate.util.arch.SharedViewModel
import kotlinx.coroutines.flow.MutableStateFlow

internal class SecondViewModel :
    SharedViewModel<SecondViewState, SecondEvent, Nothing>(),
    SecondScreen.Actions {
    override val viewState: MutableStateFlow<SecondViewState> = MutableStateFlow(SecondViewState())

    override fun onBack() = sendOutput(SecondEvent.NavigateBack)

    override fun onNext() = sendOutput(SecondEvent.NavigateNext)
}
