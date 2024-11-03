package app.futured.kmptemplate.feature_v3.ui.secondScreen

import app.futured.kmptemplate.feature_v3.ui.picker.Picker
import com.arkivanov.decompose.router.slot.ChildSlot
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable

interface SecondScreen {

    val viewState: StateFlow<SecondViewState>
    val actions: Actions
    val picker: StateFlow<ChildSlot<PickerType, Picker>>

    @Serializable
    enum class PickerType {
        FRUIT, VEGETABLE
    }

    interface Actions {
        fun onBack()
        fun onPickFruit()
        fun onPickVeggie()
        fun onPickerDismissed()
    }
}

