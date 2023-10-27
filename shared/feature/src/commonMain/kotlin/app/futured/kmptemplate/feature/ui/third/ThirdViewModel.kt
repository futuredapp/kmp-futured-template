package app.futured.kmptemplate.feature.ui.third

import app.futured.kmptemplate.util.arch.SharedViewModel
import com.arkivanov.decompose.value.MutableValue
import kotlinx.coroutines.flow.MutableStateFlow

internal class ThirdViewModel :
    SharedViewModel<ThirdViewState, ThirdEvent, Nothing>(),
    ThirdScreen.Actions {

    override val viewState: MutableStateFlow<ThirdViewState> = MutableStateFlow(ThirdViewState())

    override fun onBack() = sendOutput(ThirdEvent.NavigateBack)
}
