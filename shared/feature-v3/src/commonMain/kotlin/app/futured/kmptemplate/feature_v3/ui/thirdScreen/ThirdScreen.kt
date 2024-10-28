package app.futured.kmptemplate.feature_v3.ui.thirdScreen

import kotlinx.coroutines.flow.StateFlow

interface ThirdScreen {

    val viewState: StateFlow<ThirdViewState>
    val actions: Actions

    interface Actions {
        fun onBack()
    }
}
