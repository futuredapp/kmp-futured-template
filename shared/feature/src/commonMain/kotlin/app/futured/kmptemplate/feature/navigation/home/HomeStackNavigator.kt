package app.futured.kmptemplate.feature.navigation.home

import app.futured.kmptemplate.util.ext.asStateFlow
import app.futured.kmptemplate.util.ext.componentCoroutineScope
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack

class HomeStackNavigator(
    private val stackNavigator: StackNavigation<HomeDestination>,
) : StackNavigation<HomeDestination> by stackNavigator {

    internal fun createStack(componentContext: ComponentContext) = componentContext.childStack(
        source = stackNavigator,
        serializer = HomeDestination.serializer(),
        key = this::class.simpleName.toString(),
        initialStack = { listOf(HomeDestination.First("some")) },
        handleBackButton = true,
        childFactory = { config, childContext -> config.createComponent(childContext) },
    )
        .asStateFlow(componentContext.componentCoroutineScope())
}
