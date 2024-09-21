package app.futured.kmptemplate.feature.ui.picker

interface PickerScreen {

    val actions: Actions

    interface Actions {
        fun onPick()
    }
}
