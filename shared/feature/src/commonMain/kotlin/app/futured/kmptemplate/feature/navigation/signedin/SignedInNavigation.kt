package app.futured.kmptemplate.feature.navigation.signedin

import app.futured.kmptemplate.feature.data.model.ui.navigation.NavigationTab
import app.futured.kmptemplate.feature.navigation.signedin.tab.SignedInSlotDestination
import app.futured.kmptemplate.feature.navigation.signedin.tab.SignedInSlotNavEntry
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import kotlinx.coroutines.flow.StateFlow

interface SignedInNavigation : BackHandlerOwner {

    val stack: StateFlow<ChildStack<SignedInDestination, SignedInNavEntry>>
    val slot: StateFlow<ChildSlot<SignedInSlotDestination, SignedInSlotNavEntry>>
    val viewState: StateFlow<SignedInNavigationViewState>
    val actions: Actions

    /*
    These are direct handles to navigation tabs on iOS
     */
    val tabA: StateFlow<SignedInNavEntry.A?>
    val tabB: StateFlow<SignedInNavEntry.B?>
    val tabC: StateFlow<SignedInNavEntry.C?>

    interface Actions {
        fun onTabSelected(tab: NavigationTab)
        fun onBack()
        fun onSlotDismissed()
    }
}
