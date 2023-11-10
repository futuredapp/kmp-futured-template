package app.futured.kmptemplate.feature.navigation.home

import app.futured.kmptemplate.util.arch.Component
import app.futured.kmptemplate.util.arch.ViewModelComponent
import app.futured.kmptemplate.util.ext.viewModel
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import org.koin.core.component.inject

internal class HomeNavigationComponent(
    componentContext: ComponentContext,
) : ViewModelComponent<HomeNavigationViewModel, HomeNavigationEvent>(componentContext),
    HomeNavigation {
    private val homeNavigator: HomeNavigator by inject()

    override val stack: Value<ChildStack<HomeDestination<Component>, Component>> =
        homeNavigator.createStack(componentContext)

    override val viewModel: HomeNavigationViewModel by viewModel()
    override val output: (HomeNavigationEvent) -> Unit = {}
}
