package app.futured.kmptemplate.feature.ui.first

import app.futured.kmptemplate.util.arch.Component
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface FirstScreen : Component {
    val viewState: StateFlow<FirstViewState>
    val actions: Actions
    val events: Flow<FirstUiEvent>

    interface Actions {
        fun onBack()
        fun onNext()
    }
}
