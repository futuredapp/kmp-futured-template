package app.futured.kmptemplate.feature.ui.login

import kotlinx.coroutines.flow.StateFlow

interface LoginScreen {
    val viewState: StateFlow<LoginViewState>
    val actions: Actions
    val suspendActions: SuspendActions

    interface Actions {
        fun onLoginClick()
    }

    interface SuspendActions {

        /**
         * Demonstrates use of `refreshable` SwiftUI modifier.
         */
        suspend fun refresh()
    }
}
