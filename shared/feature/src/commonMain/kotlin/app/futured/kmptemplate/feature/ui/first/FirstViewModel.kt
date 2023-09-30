package app.futured.kmptemplate.feature.ui.first

import app.futured.kmptemplate.util.arch.SharedViewModel
import com.arkivanov.decompose.value.MutableValue

internal class FirstViewModel : SharedViewModel<FirstViewState, FirstEvent, Nothing>(),
    FirstScreen.Actions {
    override val viewState: MutableValue<FirstViewState> = MutableValue(FirstViewState())

    override fun onBack() {
        // todo
    }
}
