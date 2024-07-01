package app.futured.kmptemplate.feature.navigation.root

import app.futured.kmptemplate.feature.navigation.deeplink.DeepLinkNavigator
import app.futured.kmptemplate.feature.navigation.deeplink.DeepLinkResolver
import app.futured.kmptemplate.util.arch.ViewModelComponent
import app.futured.kmptemplate.util.ext.viewModel
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.inject

internal class RootNavigationComponent(
    componentContext: ComponentContext,
) : ViewModelComponent<RootNavigationViewModel>(componentContext),
    RootNavigation {

    private val rootNavigator: RootNavigator by inject()
    private val deepLinkResolver: DeepLinkResolver by inject()
    private val deepLinkNavigator: DeepLinkNavigator by inject()

    override val viewModel: RootNavigationViewModel by viewModel()

    override val slot: StateFlow<ChildSlot<RootDestination, RootEntry>> =
        rootNavigator.createSlot(componentContext)

    override fun openDeepLink(uri: String) {
        val deepLinkDestination = deepLinkResolver.resolve(uri) ?: return
        deepLinkNavigator.openDeepLink(deepLinkDestination)
    }
}
