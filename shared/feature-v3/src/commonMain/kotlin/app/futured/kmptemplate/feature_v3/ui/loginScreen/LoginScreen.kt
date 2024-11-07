package app.futured.kmptemplate.feature_v3.ui.loginScreen

import app.futured.arkitekt.decompose.navigation.NavigationActions
import app.futured.kmptemplate.feature_v3.ui.base.AppComponentContext
import app.futured.kmptemplate.feature_v3.ui.base.ScreenComponent
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

interface LoginScreen {
    val viewState: StateFlow<LoginViewState>
    val actions: Actions

    interface Actions {
        fun onBack()
    }
}

internal data class LoginScreenNavigation(
    val pop: () -> Unit,
) : NavigationActions

data object LoginViewState

@Factory
internal class LoginComponent(
    @InjectedParam componentContext: AppComponentContext,
    @InjectedParam override val navigation: LoginScreenNavigation,
) : ScreenComponent<LoginViewState, Nothing, LoginScreenNavigation>(componentContext, LoginViewState), LoginScreen {

    override val actions: LoginScreen.Actions = object : LoginScreen.Actions {
        override fun onBack() = navigation.pop()
    }

    override val viewState: StateFlow<LoginViewState> = componentState.whenStarted {
        // onStart
    }
}



