package app.futured.kmptemplate.feature.ui.picker

import app.futured.kmptemplate.util.arch.SharedViewModel
import kotlinx.coroutines.flow.MutableStateFlow

internal class PickerViewModel : SharedViewModel<PickerViewState, Nothing>(), PickerScreen.Actions {

    override val viewState: MutableStateFlow<PickerViewState> = MutableStateFlow(PickerViewState)

    override fun onPick() {
        TODO("Not yet implemented")
    }
}
