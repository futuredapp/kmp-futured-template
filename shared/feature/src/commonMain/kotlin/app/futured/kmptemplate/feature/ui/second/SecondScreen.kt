package app.futured.kmptemplate.feature.ui.second

import app.futured.kmptemplate.util.arch.Component
import com.arkivanov.decompose.value.MutableValue

interface SecondScreen : Component {
    val viewState: MutableValue<SecondViewState>
    val actions: Actions

    interface Actions {
        fun onBack()
        fun onNext()
    }
}
