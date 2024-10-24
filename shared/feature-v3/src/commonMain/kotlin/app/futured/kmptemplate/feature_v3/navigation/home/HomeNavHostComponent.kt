package app.futured.kmptemplate.feature_v3.navigation.home

import app.futured.arkitekt.decompose.ext.asStateFlow
import app.futured.arkitekt.decompose.ext.componentCoroutineScope
import app.futured.arkitekt.decompose.presentation.Stateless
import app.futured.kmptemplate.feature_v3.ui.base.AppComponent
import app.futured.kmptemplate.feature_v3.ui.base.AppComponentContext
import app.futured.kmptemplate.feature_v3.ui.base.ScreenComponentFactory
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.childStack
import kotlinx.coroutines.flow.StateFlow

internal class HomeNavHostComponent(
    componentContext: AppComponentContext,
) : AppComponent<Stateless, Nothing>(componentContext, Stateless), HomeNavHost {

    override fun onStart() = Unit

    private val homeNavigator = HomeNavigatorImpl()

    override val actions: HomeNavHost.Actions = homeNavigator

    override val stack: StateFlow<ChildStack<HomeConfig, HomeChild>> = childStack(
        source = homeNavigator,
        serializer = HomeConfig.serializer(),
        initialStack = { listOf(HomeConfig.First) },
        key = "HomeStack",
        handleBackButton = true,
        childFactory = { config, childCtx ->
            when (config) {
                HomeConfig.First -> HomeChild.First(ScreenComponentFactory.createComponent(childCtx, homeNavigator))
                HomeConfig.Second -> HomeChild.Second(ScreenComponentFactory.createComponent(childCtx, homeNavigator))
            }
        },
    ).asStateFlow(componentCoroutineScope())
}
