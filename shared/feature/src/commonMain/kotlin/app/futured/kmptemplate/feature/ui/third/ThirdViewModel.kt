package app.futured.kmptemplate.feature.ui.third

import app.futured.kmptemplate.util.arch.SharedViewModel
import com.arkivanov.decompose.value.MutableValue

internal class ThirdViewModel :
    SharedViewModel<ThirdViewState, ThirdEvent, Nothing>(),
    ThirdScreen.Actions {
    override val viewState: MutableValue<ThirdViewState> = MutableValue(ThirdViewState())

    override fun onBack() {
        // todo
    }
}
