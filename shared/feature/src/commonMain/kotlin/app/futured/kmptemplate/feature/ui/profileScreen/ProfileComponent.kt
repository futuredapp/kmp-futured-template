package app.futured.kmptemplate.feature.ui.profileScreen

import app.futured.kmptemplate.feature.ui.base.AppComponentContext
import app.futured.kmptemplate.feature.ui.base.ScreenComponent
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

@Factory
internal class ProfileComponent(
    @InjectedParam componentContext: AppComponentContext,
    @InjectedParam override val navigation: ProfileScreenNavigation,
) : ScreenComponent<ProfileViewState, Nothing, ProfileScreenNavigation>(
    componentContext,
    ProfileViewState,
), ProfileScreen, ProfileScreenNavigation by navigation {

    override val actions: ProfileScreen.Actions = object : ProfileScreen.Actions {
        override fun onLogout() = navigateToLogin()
        override fun onThird() = navigateToThird("hello third from profile")
    }

    override val viewState: StateFlow<ProfileViewState> = componentState
}
