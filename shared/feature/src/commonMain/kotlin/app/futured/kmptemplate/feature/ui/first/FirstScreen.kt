package app.futured.kmptemplate.feature.ui.first

import kotlinx.coroutines.flow.StateFlow

interface FirstScreen {
    val viewState: StateFlow<FirstViewState>
    val actions: Actions

    interface Actions {
        fun onBack()
        fun onNext()
    }
}
