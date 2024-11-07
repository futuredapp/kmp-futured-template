package app.futured.kmptemplate.feature_v3.navigation.signedIn

import app.futured.arkitekt.decompose.ext.asStateFlow
import app.futured.arkitekt.decompose.ext.switchTab
import app.futured.kmptemplate.feature_v3.navigation.deepLink.DeepLinkDestination
import app.futured.kmptemplate.feature_v3.navigation.deepLink.DeepLinkResolver
import app.futured.kmptemplate.feature_v3.navigation.home.HomeNavHostComponent
import app.futured.kmptemplate.feature_v3.navigation.profile.ProfileNavHostComponent
import app.futured.kmptemplate.feature_v3.ui.base.AppComponent
import app.futured.kmptemplate.feature_v3.ui.base.AppComponentContext
import app.futured.kmptemplate.feature_v3.ui.base.AppComponentFactory
import app.futured.kmptemplate.feature_v3.ui.thirdScreen.ThirdScreenArgs
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.koin.core.annotation.InjectedParam

internal class SignedInNavHostComponent(
    @InjectedParam componentContext: AppComponentContext,
    private val deepLinkResolver: DeepLinkResolver,
) : AppComponent<SignedInNavHostViewState, Nothing>(componentContext, SignedInNavHostViewState()), SignedInNavHost {

    private val stackNavigator = StackNavigation<SignedInConfig>()
    private var pendingDeepLink: DeepLinkDestination? = null

    override val stack: StateFlow<ChildStack<SignedInConfig, SignedInChild>> = childStack(
        source = stackNavigator,
        serializer = SignedInConfig.serializer(),
        initialStack = { SignedInNavHostDefaults.getInitialStack() },
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
                        config.initialStack,
                    ),
                )
            }
        },
    ).asStateFlow(componentCoroutineScope)

    override val viewState: StateFlow<SignedInNavHostViewState> = componentState.combine(stack) { state, stack ->
        state.copy(
            selectedTab = when (stack.active.instance) {
                is SignedInChild.Home -> NavigationTab.HOME
                is SignedInChild.Profile -> NavigationTab.PROFILE
            },
        )
    }.whenStarted()

    override val homeTab: StateFlow<SignedInChild.Home?> = stack.map { childStack ->
        childStack.items.map { it.instance }.filterIsInstance<SignedInChild.Home>().firstOrNull()
    }.stateIn(componentCoroutineScope, SharingStarted.Lazily, null)

    override val profileTab: StateFlow<SignedInChild.Profile?> = stack.map { childStack ->
        childStack.items.map { it.instance }.filterIsInstance<SignedInChild.Profile>().firstOrNull()
    }.stateIn(componentCoroutineScope, SharingStarted.Lazily, null)

    override val actions: SignedInNavHost.Actions = object : SignedInNavHost.Actions {

        override fun onDeepLink(uri: String) {
            val deepLinkDestination = deepLinkResolver.resolve(uri) ?: return
            pendingDeepLink = deepLinkDestination

            /*
            You might wanna defer call to this function for later,
            for example when you need to load some initial data before navigating somewhere, etc.
            */
            consumeDeepLink()
        }

        override fun onTabSelected(tab: NavigationTab) = stackNavigator.switchTab(
            when (tab) {
                NavigationTab.HOME -> SignedInConfig.Home()
                NavigationTab.PROFILE -> SignedInConfig.Profile()
            },
        )

        override fun onBack() = stackNavigator.pop()
    }

    private fun consumeDeepLink() {
        val deepLink = pendingDeepLink ?: return
        when (deepLink) {
            DeepLinkDestination.HomeTab -> stackNavigator.bringToFront(SignedInConfig.deepLinkHome())
            DeepLinkDestination.ProfileTab -> stackNavigator.bringToFront(SignedInConfig.deepLinkProfile())
            DeepLinkDestination.SecondScreen -> stackNavigator.bringToFront(SignedInConfig.deepLinkSecondScreen())
            is DeepLinkDestination.ThirdScreen -> stackNavigator.bringToFront(SignedInConfig.deepLinkThirdScreen(ThirdScreenArgs(deepLink.argument)))
        }
    }
}
