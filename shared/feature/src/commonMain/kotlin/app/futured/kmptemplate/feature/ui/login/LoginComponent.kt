package app.futured.kmptemplate.feature.ui.login

import app.futured.kmptemplate.util.arch.ViewModelComponent
import app.futured.kmptemplate.util.ext.viewModel
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue

internal class LoginComponent(
    componentContext: ComponentContext,
    override val output: (LoginEvent) -> Unit
) : ViewModelComponent<LoginViewModel, LoginEvent>(componentContext), LoginScreen, LoginScreen.Actions {
    override val viewModel: LoginViewModel by viewModel()
    override val viewState: MutableValue<LoginViewState> = MutableValue(LoginViewState())
    override val actions: LoginScreen.Actions = this
}
