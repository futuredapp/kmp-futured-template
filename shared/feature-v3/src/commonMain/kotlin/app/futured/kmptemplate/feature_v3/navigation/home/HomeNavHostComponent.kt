package app.futured.kmptemplate.feature_v3.navigation.home

import app.futured.arkitekt.decompose.ext.asStateFlow
import app.futured.arkitekt.decompose.ext.componentCoroutineScope
import app.futured.arkitekt.decompose.presentation.Stateless
import app.futured.kmptemplate.feature_v3.ui.base.AppComponent
import app.futured.kmptemplate.feature_v3.ui.base.AppComponentContext
import app.futured.kmptemplate.feature_v3.ui.firstScreen.FirstComponentFactory
import app.futured.kmptemplate.feature_v3.ui.secondScreen.SecondScreen
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
                HomeConfig.First -> FirstComponentFactory.create(
                    componentContext = childCtx,
                    navigation = homeNavigator, // What about results?
                ).let { HomeChild.First(it) }

                HomeConfig.Second -> (object : SecondScreen {}).let { HomeChild.Second(it) }
            }
        },
    ).asStateFlow(componentCoroutineScope())
}
