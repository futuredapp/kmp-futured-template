package app.futured.kmptemplate.feature.navigation.signedin

import app.futured.kmptemplate.util.arch.ViewState

data class SignedInNavigationViewState(
    val selectedTab: Tab = Tab.A,
) : ViewState {
    enum class Tab {
        A, B, C
    }
}
