package app.futured.kmptemplate.feature.ui.picker

import kotlinx.coroutines.flow.StateFlow

interface PickerScreen {
    val viewState: StateFlow<PickerState>
    val actions: Actions

    interface Actions {
        fun onPick(item: String)
        fun onDismiss()

        companion object {
            fun noOpActions(): Actions = object : Actions {
                override fun onPick(item: String) = Unit
                override fun onDismiss() = Unit
            }
        }
    }
}
