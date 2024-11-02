package app.futured.kmptemplate.feature.navigation.root

import app.futured.arkitekt.decompose.injection.viewModel
import app.futured.kmptemplate.feature.AppComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.inject

internal class RootNavigationComponent(
    componentContext: AppComponentContext,
) : AppComponentContext by componentContext,
    RootNavigation {

    private val rootNavigator: RootNavigator by inject()
    private val viewModel: RootNavigationViewModel by viewModel()

    override val actions: RootNavigation.Actions = viewModel

    override val slot: StateFlow<ChildSlot<RootDestination, RootEntry>> =
        rootNavigator.createSlot(componentContext)
}
