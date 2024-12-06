package app.futured.kmptemplate.feature.navigation.home

import app.futured.arkitekt.decompose.ext.asStateFlow
import app.futured.kmptemplate.feature.ui.base.AppComponent
import app.futured.kmptemplate.feature.ui.base.AppComponentContext
import app.futured.kmptemplate.feature.ui.base.AppComponentFactory
import app.futured.kmptemplate.feature.ui.firstScreen.FirstComponent
import app.futured.kmptemplate.feature.ui.secondScreen.SecondComponent
import app.futured.kmptemplate.feature.ui.thirdScreen.ThirdComponent
import com.arkivanov.decompose.Child
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.navigate
import com.arkivanov.decompose.router.stack.pop
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

@Factory
internal class HomeNavHostComponent(
    @InjectedParam componentContext: AppComponentContext,
    @InjectedParam private val initialStack: List<HomeConfig>,
    private val homeNavigator: HomeNavigation,
) : AppComponent<Unit, Nothing>(componentContext, Unit), HomeNavHost {


    override val actions: HomeNavHost.Actions = object : HomeNavHost.Actions {
        override fun navigate(newStack: List<Child<HomeConfig, HomeChild>>) =
            homeNavigator.navigator.navigate { newStack.map { it.configuration } }

        override fun pop() = homeNavigator.navigator.pop()
    }

    override val stack: StateFlow<ChildStack<HomeConfig, HomeChild>> = childStack(
        source = homeNavigator.navigator,
        serializer = HomeConfig.serializer(),
        initialStack = { initialStack },
        key = "HomeStack",
        handleBackButton = true,
        childFactory = { config, childCtx ->
            when (config) {
                HomeConfig.First -> HomeChild.First(
                    AppComponentFactory.createComponent<FirstComponent>(
                        childContext = childCtx,
                        navigation = homeNavigator,
                    ),
                )

                HomeConfig.Second -> HomeChild.Second(
                    AppComponentFactory.createComponent<SecondComponent>(
                        childContext = childCtx,
                        navigation = homeNavigator,
                    ),
                )

                is HomeConfig.Third -> HomeChild.Third(
                    AppComponentFactory.createComponent<ThirdComponent>(
                        childContext = childCtx,
                        navigation = homeNavigator,
                        config.args,
                    ),
                )
            }
        },
    ).asStateFlow(componentCoroutineScope)
}
