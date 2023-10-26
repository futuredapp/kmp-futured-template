package app.futured.kmptemplate.feature.navigation.home

import app.futured.kmptemplate.feature.ui.first.FirstComponent
import app.futured.kmptemplate.feature.ui.first.FirstEvent
import app.futured.kmptemplate.feature.ui.second.SecondComponent
import app.futured.kmptemplate.feature.ui.second.SecondEvent
import app.futured.kmptemplate.feature.ui.third.ThirdComponent
import app.futured.kmptemplate.feature.ui.third.ThirdEvent
import com.arkivanov.decompose.Child
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.navigate
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value

internal class HomeNavigationComponent(
    componentContext: ComponentContext,
    private val output: (HomeNavigationEvent) -> Unit,
) : HomeNavigation,
    HomeNavigation.Actions,
    ComponentContext by componentContext {

    override val actions: HomeNavigation.Actions = this
    private val stackNavigator = StackNavigation<HomeDestination>()

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

    // region HomeNavigation.Actions

    override fun iosPopTo(newStack: List<Child<HomeDestination, HomeNavigationEntry>>) {
        stackNavigator.navigate { newStack.map { it.configuration } }
    }

    // endregion

    // region Screen events

    private fun handleFirstEvents(event: FirstEvent) = when (event) {
        FirstEvent.NavigateBack -> output(HomeNavigationEvent.NavigateBack)
        FirstEvent.NavigateNext -> stackNavigator.push(HomeDestination.Second)
    }

    private fun handleSecondEvents(event: SecondEvent) = when (event) {
        SecondEvent.NavigateBack -> stackNavigator.pop()
        SecondEvent.NavigateNext -> stackNavigator.push(HomeDestination.Third)
    }

    private fun handleThirdEvents(event: ThirdEvent) = when (event) {
        ThirdEvent.NavigateBack -> stackNavigator.pop()
    }

    // endregion
}
