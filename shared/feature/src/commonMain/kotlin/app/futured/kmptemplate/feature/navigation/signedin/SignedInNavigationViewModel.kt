package app.futured.kmptemplate.feature.navigation.signedin

import app.futured.kmptemplate.feature.data.model.ui.navigation.NavigationTab
import app.futured.kmptemplate.util.arch.SharedViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.core.annotation.Factory

@Factory
internal class SignedInNavigationViewModel(
    private val signedInNavigator: SignedInNavigator,
) : SharedViewModel<SignedInNavigationViewState, Nothing>(), SignedInNavigation.Actions {

    override val viewState: MutableStateFlow<SignedInNavigationViewState> = MutableStateFlow(SignedInNavigationViewState())

    override fun onTabSelected(tab: NavigationTab) {
        signedInNavigator.switchTab(
            when (tab) {
                NavigationTab.A -> SignedInDestination.A
                NavigationTab.B -> SignedInDestination.B()
                NavigationTab.C -> SignedInDestination.C
            },
        )
    }

    override fun onBack() {
        signedInNavigator.pop()
    }
}
