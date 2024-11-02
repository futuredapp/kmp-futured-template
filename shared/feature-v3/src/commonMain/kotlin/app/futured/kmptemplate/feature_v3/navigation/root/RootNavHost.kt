package app.futured.kmptemplate.feature_v3.navigation.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import kotlinx.coroutines.flow.StateFlow

interface RootNavHost : BackHandlerOwner {

    val stack: StateFlow<ChildStack<RootConfig, RootChild>>
    val viewState: StateFlow<RootNavHostState>

    val actions: Actions

    /*
    These are direct handles to navigation tabs on iOS
     */
    val homeTab: StateFlow<RootChild.Home?>
    val profileTab: StateFlow<RootChild.Profile?>

    interface Actions {
        fun onTabSelected(tab: NavigationTab)
        fun onBack()
    }
}
