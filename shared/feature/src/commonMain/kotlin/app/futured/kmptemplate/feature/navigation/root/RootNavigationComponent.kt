package app.futured.kmptemplate.feature.navigation.root

import app.futured.kmptemplate.feature.navigation.home.HomeNavigationComponent
import app.futured.kmptemplate.feature.navigation.home.HomeNavigationEvent
import app.futured.kmptemplate.feature.ui.login.LoginComponent
import app.futured.kmptemplate.feature.ui.login.LoginEvent
import app.futured.kmptemplate.util.arch.ViewModelComponent
import app.futured.kmptemplate.util.ext.viewModel
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.value.Value

internal class RootNavigationComponent(
    componentContext: ComponentContext,
) : ViewModelComponent<RootNavigationViewModel, RootNavigationEvent>(componentContext),
    RootNavigation {

    override val viewModel: RootNavigationViewModel by viewModel()
    override val output: (RootNavigationEvent) -> Unit = ::onRootNavigationEvent

    private val slotNavigator = SlotNavigation<RootDestination>()

    override val slot: Value<ChildSlot<RootDestination, RootNavigationEntry>> = childSlot(
        source = slotNavigator,
        initialConfiguration = { RootDestination.Login },
        handleBackButton = false,
        childFactory = { destination, childContext ->
            when (destination) {
                RootDestination.Login -> RootNavigationEntry.Login(
                    screen = LoginComponent(childContext, ::handleLoginEvent),
                )

                RootDestination.Home -> RootNavigationEntry.Home(
                    navigation = HomeNavigationComponent(childContext, ::handleHomeNavigationEvent),
                )
            }
        },
    )

    private fun onRootNavigationEvent(event: RootNavigationEvent) = Unit

    private fun handleLoginEvent(loginEvent: LoginEvent) = when (loginEvent) {
        LoginEvent.NavigateToHome -> slotNavigator.activate(RootDestination.Home)
    }

    private fun handleHomeNavigationEvent(event: HomeNavigationEvent) = when (event) {
        HomeNavigationEvent.NavigateBack -> slotNavigator.activate(RootDestination.Login)
    }
}
