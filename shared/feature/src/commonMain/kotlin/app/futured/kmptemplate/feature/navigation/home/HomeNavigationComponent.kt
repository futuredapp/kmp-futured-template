package app.futured.kmptemplate.feature.navigation.home

import app.futured.kmptemplate.util.arch.Component
import app.futured.kmptemplate.util.arch.Navigator
import app.futured.kmptemplate.util.arch.ViewModelComponent
import app.futured.kmptemplate.util.ext.viewModel
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value

class HomeNavigator2 : Navigator<HomeDestination<Component>>, HomeNavigation {

    private val stackNavigator = StackNavigation<HomeDestination<Component>>()
    
    override val stack: Value<ChildStack<HomeDestination<Component>, Component>>
        get() = TODO("Not yet implemented")

    override fun push(destination: HomeDestination<Component>) {
        TODO("Not yet implemented")
    }

    override fun pop() {
        TODO("Not yet implemented")
    }
}

internal class HomeNavigationComponent(
    componentContext: ComponentContext,
) : ViewModelComponent<HomeNavigationViewModel, HomeNavigationEvent>(componentContext),
    HomeNavigation {

    private val stackNavigator = StackNavigation<HomeDestination<Component>>()

    override val stack: Value<ChildStack<HomeDestination<Component>, Component>> = childStack(
        source = stackNavigator,
        key = HomeNavigationComponent::class.simpleName.toString(),
        initialStack = { listOf(HomeDestination.First("some")) },
        handleBackButton = true,
        childFactory = ::componentFactory
    )

    private fun <C : Component> componentFactory(
        config: HomeDestination<C>,
        context: ComponentContext
    ): C = config.createComponent(context)


    override val viewModel: HomeNavigationViewModel by viewModel()

    override val output: (HomeNavigationEvent) -> Unit
        get() = TODO("Not yet implemented")
}
