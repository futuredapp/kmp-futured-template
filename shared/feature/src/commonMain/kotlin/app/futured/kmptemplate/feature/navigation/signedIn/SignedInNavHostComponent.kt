package app.futured.kmptemplate.feature.navigation.signedIn

import app.futured.arkitekt.decompose.ext.asStateFlow
import app.futured.arkitekt.decompose.ext.switchTab
import app.futured.factorygenerator.annotation.GenerateFactory
import app.futured.kmptemplate.feature.navigation.home.HomeNavHostComponentFactory
import app.futured.kmptemplate.feature.navigation.profile.ProfileNavHostComponentFactory
import app.futured.kmptemplate.feature.ui.base.AppComponent
import app.futured.kmptemplate.feature.ui.base.AppComponentContext
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

@GenerateFactory
@Factory
internal class SignedInNavHostComponent(
    @InjectedParam componentContext: AppComponentContext,
    @InjectedParam navigationToLogin: () -> Unit,
    @InjectedParam initialConfig: SignedInConfig,
) : AppComponent<SignedInNavHostViewState, Nothing>(componentContext, DEFAULT_STATE),
    SignedInNavHost {

    companion object {
        val DEFAULT_STATE = SignedInNavHostViewState()
    }

    private val stackNavigator = StackNavigation<SignedInConfig>()

    override val stack: StateFlow<ChildStack<SignedInConfig, SignedInChild>> = childStack(
        source = stackNavigator,
        serializer = SignedInConfig.serializer(),
        initialStack = { SignedInNavHostDefaults.getInitialStack(initialConfig) },
        handleBackButton = true,
        childFactory = { config, childCtx ->
            when (config) {
                is SignedInConfig.Home -> SignedInChild.Home(
                    HomeNavHostComponentFactory.createComponent(
                        componentContext = childCtx,
                        initialStack = config.initialStack,
                    ),
                )

                is SignedInConfig.Profile -> SignedInChild.Profile(
                    navHost = ProfileNavHostComponentFactory.createComponent(
                        componentContext = childCtx,
                        toLogin = navigationToLogin,
                        initialStack = config.initialStack,
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
    }.stateIn(lifecycleScope, SharingStarted.Lazily, DEFAULT_STATE)

    override val homeTab: StateFlow<SignedInChild.Home?> = stack.map { childStack ->
        childStack.items.map { it.instance }.filterIsInstance<SignedInChild.Home>().firstOrNull()
    }.stateIn(lifecycleScope, SharingStarted.Lazily, null)

    override val profileTab: StateFlow<SignedInChild.Profile?> = stack.map { childStack ->
        childStack.items.map { it.instance }.filterIsInstance<SignedInChild.Profile>().firstOrNull()
    }.stateIn(lifecycleScope, SharingStarted.Lazily, null)

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
