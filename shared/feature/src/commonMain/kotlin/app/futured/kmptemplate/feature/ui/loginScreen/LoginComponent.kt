package app.futured.kmptemplate.feature.ui.loginScreen

import app.futured.factorygenerator.annotation.GenerateFactory
import app.futured.kmptemplate.feature.domain.SetUserLoggedInUseCase
import app.futured.kmptemplate.feature.ui.base.AppComponentContext
import app.futured.kmptemplate.feature.ui.base.ScreenComponent
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

@GenerateFactory
@Factory
internal class LoginComponent(
    @InjectedParam componentContext: AppComponentContext,
    @InjectedParam override val navigation: LoginScreenNavigation,
    private val setUserLoggedInUseCase: SetUserLoggedInUseCase,
) : ScreenComponent<LoginViewState, Nothing, LoginScreenNavigation>(
    componentContext = componentContext,
    defaultState = LoginViewState,
),
    LoginScreen,
    LoginScreenNavigation by navigation,
    LoginScreen.Actions {

    override val actions: LoginScreen.Actions = this
    override val viewState: StateFlow<LoginViewState> = componentState

    override fun onLoginClick() {
        setUserLoggedInUseCase.execute(SetUserLoggedInUseCase.Args(true)) {
            onSuccess {
                navigateToSignedIn()
            }
        }
    }
}
