package app.futured.kmptemplate.feature.ui.first

import app.futured.kmptemplate.util.arch.Component
import com.arkivanov.decompose.value.MutableValue

interface FirstScreen : Component {
    val viewState: MutableValue<FirstViewState>
    val actions: Actions

    interface Actions {
        fun onBack()
        fun onNext()
    }
}
