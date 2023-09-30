package app.futured.kmptemplate.feature.ui.third

import com.arkivanov.decompose.value.MutableValue

interface ThirdScreen {
    val viewState: MutableValue<ThirdViewState>
    val actions: Actions

    interface Actions {
        fun onBack()
    }
}
