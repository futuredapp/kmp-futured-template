package app.futured.kmptemplate.feature_v3.ui.loginScreen

import kotlinx.coroutines.flow.StateFlow

interface LoginScreen {
    val viewState: StateFlow<LoginViewState>
    val actions: Actions

    interface Actions {
        fun onLoginClick()
    }
}
