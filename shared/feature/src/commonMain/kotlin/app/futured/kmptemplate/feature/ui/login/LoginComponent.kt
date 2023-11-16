package app.futured.kmptemplate.feature.ui.login

import app.futured.kmptemplate.util.arch.ViewModelComponent
import app.futured.kmptemplate.util.ext.viewModel
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow

internal class LoginComponent(
    componentContext: ComponentContext,
) : ViewModelComponent<LoginViewModel, LoginEvent>(componentContext), LoginScreen {
    override val viewModel: LoginViewModel by viewModel()
    override val viewState: StateFlow<LoginViewState> = viewModel.viewState
    override val actions: LoginScreen.Actions = viewModel
    override val suspendActions: LoginScreen.SuspendActions = viewModel
}
