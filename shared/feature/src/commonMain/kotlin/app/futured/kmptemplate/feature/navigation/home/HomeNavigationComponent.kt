package app.futured.kmptemplate.feature.navigation.home

import app.futured.kmptemplate.util.arch.Component
import app.futured.kmptemplate.util.arch.ViewModelComponent
import app.futured.kmptemplate.util.ext.viewModel
import com.arkivanov.decompose.Child
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import org.koin.core.component.inject

internal class HomeNavigationComponent(
    componentContext: ComponentContext,
) : HomeNavigation,
    HomeNavigation.Actions {
    private val homeNavigator: HomeNavigator by inject()

    override val stack: StateFlow<ChildStack<HomeDestination<Component>, Component>> =
        homeNavigator.createStack(componentContext)

    override val actions: HomeNavigation.Actions = this

    override fun iosPopTo(newStack: List<Child<HomeDestination, HomeNavigationEntry>>) {
        stackNavigator.navigate { newStack.map { it.configuration } }
    }
}
