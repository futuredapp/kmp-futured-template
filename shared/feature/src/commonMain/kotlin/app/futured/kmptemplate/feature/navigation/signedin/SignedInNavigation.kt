package app.futured.kmptemplate.feature.navigation.signedin

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import kotlinx.coroutines.flow.StateFlow

interface SignedInNavigation : BackHandlerOwner {

    val stack: StateFlow<ChildStack<SignedInDestination, SignedInNavEntry>>
    val viewState: StateFlow<SignedInNavigationViewState>
    val actions: Actions

    val tabA: StateFlow<SignedInNavEntry.A?>
    val tabB: StateFlow<SignedInNavEntry.B?>
    val tabC: StateFlow<SignedInNavEntry.C?>

    interface Actions {
        fun onTabSelected(tab: SignedInNavigationViewState.Tab)
        fun onBack()
    }
}
