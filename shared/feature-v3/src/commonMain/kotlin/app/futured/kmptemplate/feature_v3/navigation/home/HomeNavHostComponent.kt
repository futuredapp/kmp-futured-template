package app.futured.kmptemplate.feature_v3.navigation.home

import app.futured.arkitekt.decompose.ext.asStateFlow
import app.futured.arkitekt.decompose.ext.componentCoroutineScope
import app.futured.arkitekt.decompose.presentation.Stateless
import app.futured.kmptemplate.feature_v3.ui.base.AppComponent
import app.futured.kmptemplate.feature_v3.ui.base.AppComponentContext
import app.futured.kmptemplate.feature_v3.ui.base.ScreenComponentFactory
import app.futured.kmptemplate.feature_v3.ui.firstScreen.FirstComponent
import app.futured.kmptemplate.feature_v3.ui.firstScreen.FirstScreenNavigation
import app.futured.kmptemplate.feature_v3.ui.secondScreen.SecondComponent
import app.futured.kmptemplate.feature_v3.ui.secondScreen.SecondScreenNavigation
import com.arkivanov.decompose.Child
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.navigate
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import kotlinx.coroutines.flow.StateFlow

internal class HomeNavHostComponent(
    componentContext: AppComponentContext,
) : AppComponent<Stateless, Nothing>(componentContext, Stateless), HomeNavHost {

    override fun onStart() = Unit

    private val homeNavigator = StackNavigation<HomeConfig>()

    override val actions: HomeNavHost.Actions = object : HomeNavHost.Actions {
        override fun navigate(newStack: List<Child<HomeConfig, HomeChild>>) = homeNavigator.navigate { newStack.map { it.configuration } }
        override fun pop() = homeNavigator.pop()
    }

    override val stack: StateFlow<ChildStack<HomeConfig, HomeChild>> = childStack(
        source = homeNavigator,
        serializer = HomeConfig.serializer(),
        initialStack = { listOf(HomeConfig.First) },
        key = "HomeStack",
        handleBackButton = true,
        childFactory = { config, childCtx ->
            when (config) {
                HomeConfig.First -> HomeChild.First(
                    ScreenComponentFactory.createComponent<FirstComponent>(
                        childContext = childCtx,
                        navigation = FirstScreenNavigation(
                            pop = { homeNavigator.pop() },
                            toSecond = { homeNavigator.pushNew(HomeConfig.Second) },
                        ),
                    ),
                )

                HomeConfig.Second -> HomeChild.Second(
                    ScreenComponentFactory.createComponent<SecondComponent>(
                        childContext = childCtx,
                        navigation = SecondScreenNavigation(
                            pop = { homeNavigator.pop() },
                        ),
                    ),
                )
            }
        },
    ).asStateFlow(componentCoroutineScope())
}
