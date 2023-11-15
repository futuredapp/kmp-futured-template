package app.futured.kmptemplate.feature.ui.third

import app.futured.kmptemplate.util.arch.Component
import kotlinx.coroutines.flow.StateFlow

interface ThirdScreen : Component {
    val viewState: StateFlow<ThirdViewState>
    val actions: Actions

    interface Actions {
        fun onBack()
    }
}
