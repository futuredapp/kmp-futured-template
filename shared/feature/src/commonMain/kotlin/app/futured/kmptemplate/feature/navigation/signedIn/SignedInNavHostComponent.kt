package app.futured.kmptemplate.feature.navigation.signedIn

import app.futured.arkitekt.decompose.ext.asStateFlow
import app.futured.arkitekt.decompose.ext.switchTab
import app.futured.kmptemplate.feature.navigation.home.HomeNavHostComponent
import app.futured.kmptemplate.feature.navigation.profile.ProfileNavHostComponent
import app.futured.kmptemplate.feature.navigation.profile.ProfileNavHostNavigation
import app.futured.kmptemplate.feature.ui.base.AppComponent
import app.futured.kmptemplate.feature.ui.base.AppComponentContext
import app.futured.kmptemplate.feature.ui.base.AppComponentFactory
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

@Factory
internal class SignedInNavHostComponent(
    @InjectedParam componentContext: AppComponentContext,
    @InjectedParam navigationActions: SignedInNavHostNavigation,
    @InjectedParam initialConfig: SignedInConfig,
) : AppComponent<SignedInNavHostViewState, Nothing>(componentContext, SignedInNavHostViewState()), SignedInNavHost {

    private val stackNavigator = StackNavigation<SignedInConfig>()

    override val stack: StateFlow<ChildStack<SignedInConfig, SignedInChild>> = childStack(
        source = stackNavigator,
        serializer = SignedInConfig.serializer(),
        initialStack = { SignedInNavHostDefaults.getInitialStack(initialConfig) },
        handleBackButton = true,
        childFactory = { config, childCtx ->
            when (config) {
                is SignedInConfig.Home -> SignedInChild.Home(
                    AppComponentFactory.createComponent<HomeNavHostComponent>(
                        childContext = childCtx,
                        config.initialStack,
                    ),
                )

                is SignedInConfig.Profile -> SignedInChild.Profile(
                    AppComponentFactory.createComponent<ProfileNavHostComponent>(
                        childContext = childCtx,
                        ProfileNavHostNavigation(
                            toLogin = navigationActions.toLogin,
                        ),
                        config.initialStack,
                    ),
                )
            }
        },
    ).asStateFlow()

    override val viewState: StateFlow<SignedInNavHostViewState> = componentState.combine(stack) { state, stack ->
        state.copy(
            selectedTab = when (stack.active.instance) {
                is SignedInChild.Home -> NavigationTab.HOME
                is SignedInChild.Profile -> NavigationTab.PROFILE
            },
        )
    }.asStateFlow()

    override val homeTab: StateFlow<SignedInChild.Home?> = stack.map { childStack ->
        childStack.items.map { it.instance }.filterIsInstance<SignedInChild.Home>().firstOrNull()
    }.stateIn(componentCoroutineScope, SharingStarted.Lazily, null)

    override val profileTab: StateFlow<SignedInChild.Profile?> = stack.map { childStack ->
        childStack.items.map { it.instance }.filterIsInstance<SignedInChild.Profile>().firstOrNull()
    }.stateIn(componentCoroutineScope, SharingStarted.Lazily, null)

    override val actions: SignedInNavHost.Actions = object : SignedInNavHost.Actions {

        override fun onTabSelected(tab: NavigationTab) = stackNavigator.switchTab(
            when (tab) {
                NavigationTab.HOME -> SignedInConfig.Home()
                NavigationTab.PROFILE -> SignedInConfig.Profile()
            },
        )

        override fun onBack() = stackNavigator.pop()
    }
}
