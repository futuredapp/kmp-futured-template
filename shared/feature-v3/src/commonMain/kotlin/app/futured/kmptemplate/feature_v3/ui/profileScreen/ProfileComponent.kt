package app.futured.kmptemplate.feature_v3.ui.profileScreen

import app.futured.kmptemplate.feature_v3.ui.base.AppComponentContext
import app.futured.kmptemplate.feature_v3.ui.base.ScreenComponent
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

@Factory
internal class ProfileComponent(
    @InjectedParam componentContext: AppComponentContext,
    @InjectedParam override val navigation: ProfileScreenNavigation,
) : ScreenComponent<ProfileViewState, Nothing, ProfileScreenNavigation>(
    componentContext,
    ProfileViewState
), ProfileScreen {

    override val actions: ProfileScreen.Actions = object : ProfileScreen.Actions {
        override fun onBack() = navigation.pop()
    }

    override val viewState: StateFlow<ProfileViewState> = componentState
}
