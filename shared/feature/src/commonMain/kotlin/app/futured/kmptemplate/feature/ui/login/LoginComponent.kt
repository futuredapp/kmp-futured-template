package app.futured.kmptemplate.feature.ui.login

import app.futured.kmptemplate.util.arch.AppComponentContext
import app.futured.kmptemplate.util.arch.viewModel
import kotlinx.coroutines.flow.StateFlow

internal class LoginComponent(
    componentContext: AppComponentContext,
) : AppComponentContext by componentContext, LoginScreen {

    private val viewModel: LoginViewModel by viewModel()
    override val viewState: StateFlow<LoginViewState> = viewModel.viewState
    override val actions: LoginScreen.Actions = viewModel
    override val suspendActions: LoginScreen.SuspendActions = viewModel
}
