package app.futured.kmptemplate.feature.navigation.home

import app.futured.kmptemplate.util.arch.Component
import app.futured.kmptemplate.util.arch.StackNavigator
import app.futured.kmptemplate.util.ext.asStateFlow
import app.futured.kmptemplate.util.ext.componentCoroutineScope
import com.arkivanov.decompose.Child
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import kotlinx.coroutines.flow.StateFlow

interface HomeNavigation : Component {
    val stack: StateFlow<ChildStack<HomeDestination, HomeEntry>>
    val actions: Actions

    interface Actions {
        fun iosPopTo(newStack: List<Child<HomeDestination, HomeEntry>>)
    }
}

class HomeStackNavigator(
    private val stackNavigator: StackNavigation<HomeDestination>,
) : StackNavigator<HomeDestination>, StackNavigation<HomeDestination> by stackNavigator {

    fun createStack(componentContext: ComponentContext) = componentContext.childStack(
        source = stackNavigator,
        key = this::class.simpleName.toString(),
        initialStack = { listOf(HomeDestination.First("some")) },
        handleBackButton = true,
        childFactory = { config, childContext -> config.createComponent(childContext) },
    )
        .asStateFlow(componentContext.componentCoroutineScope())
}
