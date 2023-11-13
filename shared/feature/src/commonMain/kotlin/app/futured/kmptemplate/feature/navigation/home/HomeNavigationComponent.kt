package app.futured.kmptemplate.feature.navigation.home

import com.arkivanov.decompose.Child
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.navigate
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class HomeNavigationComponent(
    componentContext: ComponentContext,
) : HomeNavigation, HomeNavigation.Actions, ComponentContext by componentContext, KoinComponent {
    private val homeNavigator: HomeStackNavigator by inject()

    override val stack: StateFlow<ChildStack<HomeDestination, HomeEntry>> =
        homeNavigator.createStack(componentContext)

    override val actions: HomeNavigation.Actions = this

    override fun iosPopTo(newStack: List<Child<HomeDestination, HomeEntry>>) {
        homeNavigator.navigate { newStack.map { it.configuration } }
    }
}
