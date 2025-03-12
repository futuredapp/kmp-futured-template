package app.futured.kmptemplate.feature.navigation.home

import app.futured.arkitekt.decompose.ext.asStateFlow
import app.futured.factorygenerator.annotation.GenerateFactory
import app.futured.kmptemplate.feature.ui.base.AppComponent
import app.futured.kmptemplate.feature.ui.base.AppComponentContext
import app.futured.kmptemplate.feature.ui.firstScreen.FirstComponentFactory
import app.futured.kmptemplate.feature.ui.secondScreen.SecondComponentFactory
import app.futured.kmptemplate.feature.ui.thirdScreen.ThirdComponentFactory
import com.arkivanov.decompose.Child
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.navigate
import com.arkivanov.decompose.router.stack.pop
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

@GenerateFactory
@Factory
internal class HomeNavHostComponent(
    @InjectedParam componentContext: AppComponentContext,
    @InjectedParam private val initialStack: List<HomeConfig>,
) : AppComponent<Unit, Nothing>(componentContext, Unit), HomeNavHost {

    private val homeNavigator: HomeNavigation = HomeNavigator()

    override val stack: StateFlow<ChildStack<HomeConfig, HomeChild>> = childStack(
        source = homeNavigator.navigator,
        serializer = HomeConfig.serializer(),
        initialStack = { initialStack },
        key = "HomeStack",
        handleBackButton = true,
        childFactory = { config, childCtx ->
            when (config) {
                HomeConfig.First -> HomeChild.First(FirstComponentFactory.createComponent(childCtx, homeNavigator))
                HomeConfig.Second -> HomeChild.Second(SecondComponentFactory.createComponent(childCtx, homeNavigator))
                is HomeConfig.Third -> HomeChild.Third(ThirdComponentFactory.createComponent(childCtx, homeNavigator, config.args))
            }
        },
    ).asStateFlow()

    override val actions: HomeNavHost.Actions = object : HomeNavHost.Actions {
        override fun navigate(newStack: List<Child<HomeConfig, HomeChild>>) =
            homeNavigator.navigator.navigate { newStack.map { it.configuration } }

        override fun pop() = homeNavigator.navigator.pop()
    }
}
