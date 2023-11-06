package app.futured.kmptemplate.feature.ui.second

import com.arkivanov.decompose.value.MutableValue

interface SecondScreen {
    val viewState: MutableValue<SecondViewState>
    val actions: Actions

    interface Actions {
        fun onBack()
        fun onNext()
    }
}
