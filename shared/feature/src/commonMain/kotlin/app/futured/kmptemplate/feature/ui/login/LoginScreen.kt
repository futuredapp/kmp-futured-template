package app.futured.kmptemplate.feature.ui.login

import app.futured.kmptemplate.util.arch.Component
import kotlinx.coroutines.flow.StateFlow

interface LoginScreen : Component {
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
