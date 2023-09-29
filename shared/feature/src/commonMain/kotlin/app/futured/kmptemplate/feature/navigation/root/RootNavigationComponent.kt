package app.futured.kmptemplate.feature.navigation.root

import app.futured.kmptemplate.util.arch.ViewModelComponent
import app.futured.kmptemplate.util.ext.viewModel
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.value.Value

internal class RootNavigationComponent(
    componentContext: ComponentContext,
) : ViewModelComponent<RootNavigationViewModel, RootNavigationEvent>(componentContext),
    RootNavigation {

    override val viewModel: RootNavigationViewModel by viewModel()
    override val output: (RootNavigationEvent) -> Unit = ::onViewModelEvent

    private val slotNavigator = SlotNavigation<RootDestination>()
    override val slot: Value<ChildSlot<RootDestination, RootNavigationEntry>> = childSlot(
        source = slotNavigator,
        initialConfiguration = { null },
        handleBackButton = false,
        childFactory = { destination, childContext ->
            when (destination) {
                RootDestination.Home -> TODO()
                RootDestination.Login -> TODO()
            }
        },
    )

    private fun onViewModelEvent(event: RootNavigationEvent) = when (event) {
        else -> {/*todo*/
        }
    }
}
