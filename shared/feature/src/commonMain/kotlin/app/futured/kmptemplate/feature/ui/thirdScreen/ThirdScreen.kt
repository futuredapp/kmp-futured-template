package app.futured.kmptemplate.feature.ui.thirdScreen

import kotlinx.coroutines.flow.StateFlow

interface ThirdScreen {

    val viewState: StateFlow<ThirdViewState>
    val actions: Actions

    interface Actions {
        fun onBack()
    }
}
