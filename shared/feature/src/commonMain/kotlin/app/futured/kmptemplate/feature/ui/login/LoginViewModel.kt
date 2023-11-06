package app.futured.kmptemplate.feature.ui.login

import app.futured.kmptemplate.util.arch.SharedViewModel
import com.arkivanov.decompose.value.MutableValue

internal class LoginViewModel : SharedViewModel<LoginViewState, LoginEvent, Nothing>(),
    LoginScreen.Actions {
    override val viewState: MutableValue<LoginViewState> = MutableValue(LoginViewState())

    override fun onNavigateHome() {
        sendOutput(LoginEvent.NavigateHomeEvent)
    }
}
