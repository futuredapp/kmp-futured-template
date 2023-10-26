package app.futured.kmptemplate.feature.ui.login

import com.arkivanov.decompose.value.MutableValue

interface LoginScreen {
    val viewState: MutableValue<LoginViewState>
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
