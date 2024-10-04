package app.futured.kmptemplate.feature.ui.first

import app.futured.kmptemplate.feature.ui.picker.PickerResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface FirstScreen {
    val viewState: StateFlow<FirstViewState>
    val actions: Actions
    val events: Flow<FirstUiEvent>

    interface Actions {
        fun onBackClick()
        fun onNextClick()
        fun onPickerClick()
        fun onPickerResult(result: PickerResult)
    }
}
