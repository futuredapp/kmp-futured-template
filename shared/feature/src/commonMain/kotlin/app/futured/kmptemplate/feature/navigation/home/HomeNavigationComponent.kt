package app.futured.kmptemplate.feature.navigation.home

import app.futured.kmptemplate.feature.ui.first.FirstComponent
import app.futured.kmptemplate.feature.ui.first.FirstEvent
import app.futured.kmptemplate.feature.ui.second.SecondComponent
import app.futured.kmptemplate.feature.ui.second.SecondEvent
import app.futured.kmptemplate.feature.ui.third.ThirdComponent
import app.futured.kmptemplate.feature.ui.third.ThirdEvent
import app.futured.kmptemplate.util.arch.ViewModelComponent
import app.futured.kmptemplate.util.ext.viewModel
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value

internal class HomeNavigationComponent(
    componentContext: ComponentContext,
) : ViewModelComponent<HomeNavigationViewModel, HomeNavigationEvent>(componentContext),
    HomeNavigation {

    private val stackNavigator = StackNavigation<HomeDestination>()

    override val viewModel: HomeNavigationViewModel by viewModel()
    override val stack: Value<ChildStack<HomeDestination, HomeNavigationEntry>> = childStack(
        source = stackNavigator,
        key = HomeNavigationComponent::class.simpleName.toString(),
        initialStack = { listOf(HomeDestination.First) },
        handleBackButton = true,
        childFactory = { configuration, childContext ->
            when (configuration) {
                HomeDestination.First -> FirstComponent(childContext, ::handleFirstEvents)
                    .let { HomeNavigationEntry.First(it) }

                HomeDestination.Third -> ThirdComponent(childContext, ::handleThirdEvents)
                    .let { HomeNavigationEntry.Third(it) }

                HomeDestination.Second -> SecondComponent(childContext, ::handleSecondEvents)
                    .let { HomeNavigationEntry.Second(it) }
            }
        },
    )

    override val output: (HomeNavigationEvent) -> Unit
        get() = TODO("Not yet implemented")

    private fun handleFirstEvents(event: FirstEvent) {
        // todo
    }

    private fun handleSecondEvents(event: SecondEvent) {
        // todo
    }

    private fun handleThirdEvents(event: ThirdEvent) {
        // todo
    }
}
