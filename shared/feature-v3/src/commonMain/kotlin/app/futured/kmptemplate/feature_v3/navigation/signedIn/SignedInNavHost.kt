package app.futured.kmptemplate.feature_v3.navigation.signedIn

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import kotlinx.coroutines.flow.StateFlow

interface SignedInNavHost : BackHandlerOwner {

    val stack: StateFlow<ChildStack<SignedInConfig, SignedInChild>>
    val viewState: StateFlow<SignedInNavHostViewState>

    val actions: Actions

    /*
    These are direct handles to navigation tabs on iOS
     */
    val homeTab: StateFlow<SignedInChild.Home?>
    val profileTab: StateFlow<SignedInChild.Profile?>

    interface Actions {
        fun onDeepLink(uri: String)
        fun onTabSelected(tab: NavigationTab)
        fun onBack()
    }
}
