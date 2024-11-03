package app.futured.kmptemplate.feature_v3.navigation.root

import app.futured.arkitekt.decompose.ext.asStateFlow
import app.futured.arkitekt.decompose.ext.componentCoroutineScope
import app.futured.arkitekt.decompose.ext.switchTab
import app.futured.kmptemplate.feature_v3.navigation.home.HomeNavHostComponent
import app.futured.kmptemplate.feature_v3.navigation.profile.ProfileNavHostComponent
import app.futured.kmptemplate.feature_v3.ui.base.AppComponent
import app.futured.kmptemplate.feature_v3.ui.base.AppComponentContext
import app.futured.kmptemplate.feature_v3.ui.base.AppComponentFactory
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.koin.core.annotation.InjectedParam

internal class RootNavHostComponent(
    @InjectedParam componentContext: AppComponentContext,
) : AppComponent<RootNavHostState, Nothing>(componentContext, RootNavHostState()), RootNavHost {

    private val stackNavigator = StackNavigation<RootConfig>()

    override val stack: StateFlow<ChildStack<RootConfig, RootChild>> = childStack(
        source = stackNavigator,
        serializer = RootConfig.serializer(),
        initialStack = { RootNavHostDefaults.getInitialStack() },
        handleBackButton = true,
        childFactory = { config, childCtx ->
            when (config) {
                is RootConfig.Home -> RootChild.Home(
                    AppComponentFactory.createComponent<HomeNavHostComponent>(
                        childContext = childCtx,
                        config.initialStack,
                    ),
                )

                is RootConfig.Profile -> RootChild.Profile(
                    AppComponentFactory.createComponent<ProfileNavHostComponent>(
                        childContext = childCtx,
                        config.initialStack,
                    ),
                )
            }
        },
    ).asStateFlow(componentCoroutineScope())

    override val viewState: StateFlow<RootNavHostState> = componentState.combine(stack) { state, stack ->
        state.copy(
            selectedTab = when (stack.active.instance) {
                is RootChild.Home -> NavigationTab.HOME
                is RootChild.Profile -> NavigationTab.PROFILE
            },
        )
    }.whenStarted()

    override val homeTab: StateFlow<RootChild.Home?> = stack.map { childStack ->
        childStack.items.map { it.instance }.filterIsInstance<RootChild.Home>().firstOrNull()
    }.stateIn(componentCoroutineScope, SharingStarted.Lazily, null)

    override val profileTab: StateFlow<RootChild.Profile?> = stack.map { childStack ->
        childStack.items.map { it.instance }.filterIsInstance<RootChild.Profile>().firstOrNull()
    }.stateIn(componentCoroutineScope, SharingStarted.Lazily, null)

    override val actions: RootNavHost.Actions = object : RootNavHost.Actions {
        override fun onTabSelected(tab: NavigationTab) = stackNavigator.switchTab(
            when (tab) {
                NavigationTab.HOME -> RootConfig.Home()
                NavigationTab.PROFILE -> RootConfig.Profile()
            },
        )

        override fun onBack() = stackNavigator.pop()
    }
}
