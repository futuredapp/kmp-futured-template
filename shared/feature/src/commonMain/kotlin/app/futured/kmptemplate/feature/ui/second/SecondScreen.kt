package app.futured.kmptemplate.feature.ui.second

import app.futured.kmptemplate.util.arch.Component
import com.arkivanov.decompose.value.MutableValue
import kotlinx.coroutines.flow.StateFlow

interface SecondScreen : Component {
    val viewState: StateFlow<SecondViewState>
    val actions: Actions

    interface Actions {
        fun onBack()
        fun onNext()
    }
}
