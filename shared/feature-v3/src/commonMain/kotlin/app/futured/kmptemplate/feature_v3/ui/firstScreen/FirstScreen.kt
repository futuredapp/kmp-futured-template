package app.futured.kmptemplate.feature_v3.ui.firstScreen

import app.futured.arkitekt.decompose.navigation.NavigationActions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface FirstScreen {
    val viewState: StateFlow<FirstViewState>
    val actions: Actions
    val events: Flow<FirstUiEvent>

    interface Actions {
        fun onBack()
        fun onNext()
    }
}

interface FirstScreenNavigationActions : NavigationActions {
    fun pop()
    fun navigateToSecond()
}
