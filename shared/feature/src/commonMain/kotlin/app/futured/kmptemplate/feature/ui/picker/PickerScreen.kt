package app.futured.kmptemplate.feature.ui.picker

import kotlinx.coroutines.flow.StateFlow

interface PickerScreen {

    val viewState: StateFlow<PickerViewState>
    val actions: Actions

    interface Actions {
        fun onItemResult(item: PickerViewState.Item)
        fun onDismissResult()
    }
}
