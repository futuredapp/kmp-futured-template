package app.futured.kmptemplate.feature.ui.loginScreen

import app.futured.kmptemplate.feature.ui.base.AppComponentContext
import app.futured.kmptemplate.feature.ui.base.ScreenComponent
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

@Factory
internal class LoginComponent(
    @InjectedParam componentContext: AppComponentContext,
    @InjectedParam override val navigation: LoginScreenNavigation,
) : ScreenComponent<LoginViewState, Nothing, LoginScreenNavigation>(
        componentContext,
        LoginViewState,
    ),
    LoginScreen {

    override val actions: LoginScreen.Actions = object : LoginScreen.Actions {
        override fun onLoginClick() = navigation.toSignedIn()
    }

    override val viewState: StateFlow<LoginViewState> = componentState.asStateFlow()
}
