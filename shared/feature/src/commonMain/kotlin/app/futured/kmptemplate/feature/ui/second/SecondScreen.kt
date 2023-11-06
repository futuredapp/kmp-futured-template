package app.futured.kmptemplate.feature.ui.second

import kotlinx.coroutines.flow.StateFlow

interface SecondScreen {
    val viewState: StateFlow<SecondViewState>
    val actions: Actions

    interface Actions {
        fun onBack()
        fun onNext()
    }
}
