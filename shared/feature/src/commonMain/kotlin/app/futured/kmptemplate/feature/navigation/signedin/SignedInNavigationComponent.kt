package app.futured.kmptemplate.feature.navigation.signedin

import app.futured.kmptemplate.feature.data.model.ui.navigation.NavigationTab
import app.futured.kmptemplate.util.arch.ViewModelComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.inject

internal class SignedInNavigationComponent(
    componentContext: ComponentContext,
) : ViewModelComponent<SignedInNavigationViewModel>(componentContext), SignedInNavigation {

    private val navigator: SignedInNavigator by inject()

    override val viewModel: SignedInNavigationViewModel by inject()

    override val stack: StateFlow<ChildStack<SignedInDestination, SignedInNavEntry>> = navigator.createStack(
        componentContext = this,
    )

    override val tabA: StateFlow<SignedInNavEntry.A?> = stack.map { childStack ->
        childStack.items.map { it.instance }.filterIsInstance<SignedInNavEntry.A>().firstOrNull()
    }.stateIn(coroutineScope, SharingStarted.Lazily, null)

    override val tabB: StateFlow<SignedInNavEntry.B?> = stack.map { childStack ->
        childStack.items.map { it.instance }.filterIsInstance<SignedInNavEntry.B>().firstOrNull()
    }.stateIn(coroutineScope, SharingStarted.Lazily, null)

    override val tabC: StateFlow<SignedInNavEntry.C?> = stack.map { childStack ->
        childStack.items.map { it.instance }.filterIsInstance<SignedInNavEntry.C>().firstOrNull()
    }.stateIn(coroutineScope, SharingStarted.Lazily, null)

    override val viewState: StateFlow<SignedInNavigationViewState> = viewModel.viewState.combine(stack) { vs, stack ->
        vs.copy(
            selectedTab = when (stack.active.instance) {
                is SignedInNavEntry.A -> NavigationTab.A
                is SignedInNavEntry.B -> NavigationTab.B
                is SignedInNavEntry.C -> NavigationTab.C
            },
        )
    }.stateIn(coroutineScope, SharingStarted.Lazily, SignedInNavigationViewState())

    override val actions: SignedInNavigation.Actions = viewModel
}
