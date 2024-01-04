package app.futured.kmptemplate.feature.navigation.home

import app.futured.kmptemplate.feature.navigation.deeplink.DeepLinkDestination
import app.futured.kmptemplate.util.ext.asStateFlow
import app.futured.kmptemplate.util.ext.componentCoroutineScope
import com.arkivanov.decompose.Child
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.navigate
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Single

internal interface HomeStackNavigator {
    fun createStack(
        componentContext: ComponentContext,
        deepLinkDestination: DeepLinkDestination?,
    ): StateFlow<ChildStack<HomeDestination, HomeEntry>>

    fun pop()
    fun iosPop(newStack: List<Child<HomeDestination, HomeEntry>>)

    fun navigateToSecond()
    fun navigateToThird()
}

@Single
internal class HomeStackNavigatorImpl : HomeStackNavigator {

    private val stackNavigator: StackNavigation<HomeDestination> = StackNavigation()

    override fun createStack(
        componentContext: ComponentContext,
        deepLinkDestination: DeepLinkDestination?,
    ): StateFlow<ChildStack<HomeDestination, HomeEntry>> = componentContext.childStack(
        source = stackNavigator,
        serializer = HomeDestination.serializer(),
        key = this::class.simpleName.toString(),
        initialStack = {
            when (deepLinkDestination) {
                null -> listOf(
                    HomeDestination.First("some"),
                )

                is DeepLinkDestination.SecretScreen -> listOf(
                    HomeDestination.Secret(argument = deepLinkDestination.argument),
                )

                DeepLinkDestination.ThirdScreen -> listOf(
                    HomeDestination.First("some"),
                    HomeDestination.Second,
                    HomeDestination.Third,
                )
            }
        },
        handleBackButton = true,
        childFactory = { config, childContext -> config.createComponent(childContext) },
    )
        .asStateFlow(componentContext.componentCoroutineScope())

    override fun pop() = stackNavigator.pop()

    override fun iosPop(newStack: List<Child<HomeDestination, HomeEntry>>) =
        stackNavigator.navigate { newStack.map { it.configuration } }

    override fun navigateToSecond() = stackNavigator.push(HomeDestination.Second)

    override fun navigateToThird() = stackNavigator.push(HomeDestination.Third)
}
