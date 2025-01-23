package app.futured.kmptemplate.feature.navigation.home

import app.futured.arkitekt.decompose.ext.asStateFlow
import app.futured.kmptemplate.feature.ui.base.AppComponent
import app.futured.kmptemplate.feature.ui.base.AppComponentContext
import app.futured.kmptemplate.feature.ui.base.AppComponentFactory
import app.futured.kmptemplate.feature.ui.firstScreen.FirstComponent
import app.futured.kmptemplate.feature.ui.firstScreen.FirstScreenNavigation
import app.futured.kmptemplate.feature.ui.secondScreen.SecondComponent
import app.futured.kmptemplate.feature.ui.secondScreen.SecondScreenNavigation
import app.futured.kmptemplate.feature.ui.thirdScreen.ThirdComponent
import app.futured.kmptemplate.feature.ui.thirdScreen.ThirdScreenArgs
import app.futured.kmptemplate.feature.ui.thirdScreen.ThirdScreenNavigation
import com.arkivanov.decompose.Child
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.navigate
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

@Factory
internal class HomeNavHostComponent(
    @InjectedParam componentContext: AppComponentContext,
    @InjectedParam private val initialStack: List<HomeConfig>,
) : AppComponent<Unit, Nothing>(componentContext, Unit), HomeNavHost {

    private val homeNavigator = StackNavigation<HomeConfig>()

    override val actions: HomeNavHost.Actions = object : HomeNavHost.Actions {
        override fun navigate(newStack: List<Child<HomeConfig, HomeChild>>) = homeNavigator.navigate { newStack.map { it.configuration } }
        override fun pop() = homeNavigator.pop()
    }

    override val stack: StateFlow<ChildStack<HomeConfig, HomeChild>> = childStack(
        source = homeNavigator,
        serializer = HomeConfig.serializer(),
        initialStack = { initialStack },
        key = "HomeStack",
        handleBackButton = true,
        childFactory = { config, childCtx ->
            when (config) {
                HomeConfig.First -> HomeChild.First(
                    AppComponentFactory.createComponent<FirstComponent>(
                        childContext = childCtx,
                        navigation = FirstScreenNavigation(
                            toSecond = { homeNavigator.pushNew(HomeConfig.Second) },
                        ),
                    ),
                )

                HomeConfig.Second -> HomeChild.Second(
                    AppComponentFactory.createComponent<SecondComponent>(
                        childContext = childCtx,
                        navigation = SecondScreenNavigation(
                            pop = { homeNavigator.pop() },
                            toThird = { id -> homeNavigator.pushNew(HomeConfig.Third(ThirdScreenArgs(id))) },
                        ),
                    ),
                )

                is HomeConfig.Third -> HomeChild.Third(
                    AppComponentFactory.createComponent<ThirdComponent>(
                        childContext = childCtx,
                        navigation = ThirdScreenNavigation(
                            pop = { homeNavigator.pop() },
                        ),
                        config.args,
                    ),
                )
            }
        },
    ).asStateFlow()
}
