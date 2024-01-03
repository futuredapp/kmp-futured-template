package app.futured.kmptemplate.feature.ui.third

import kotlinx.coroutines.flow.StateFlow

interface ThirdScreen {
    val viewState: StateFlow<ThirdViewState>
    val actions: Actions

    interface Actions {
        fun onBack()
    }
}
