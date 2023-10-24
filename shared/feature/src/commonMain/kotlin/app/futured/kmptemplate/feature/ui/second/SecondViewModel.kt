package app.futured.kmptemplate.feature.ui.second

import app.futured.kmptemplate.util.arch.SharedViewModel
import com.arkivanov.decompose.value.MutableValue

internal class SecondViewModel :
    SharedViewModel<SecondViewState, SecondEvent, Nothing>(),
    SecondScreen.Actions {
    override val viewState: MutableValue<SecondViewState> = MutableValue(SecondViewState())

    override fun onBack() {
        // todo
    }
}
