package app.futured.kmptemplate.feature.ui.login

import com.arkivanov.decompose.value.MutableValue

interface LoginScreen {
    val viewState: MutableValue<LoginViewState>
    val actions: Actions

    interface Actions {
        fun onNavigateHome()
    }
}
