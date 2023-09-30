package app.futured.kmptemplate.feature.ui.first

import com.arkivanov.decompose.value.MutableValue

interface FirstScreen {
    val viewState: MutableValue<FirstViewState>
    val actions: Actions

    interface Actions {
        fun onBack()
    }
}
