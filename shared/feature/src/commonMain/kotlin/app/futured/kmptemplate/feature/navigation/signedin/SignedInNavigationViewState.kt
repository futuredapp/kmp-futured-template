package app.futured.kmptemplate.feature.navigation.signedin

import app.futured.arkitekt.decompose.presentation.ViewState
import app.futured.kmptemplate.feature.data.model.ui.navigation.NavigationTab
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class SignedInNavigationViewState(
    val navigationTabs: ImmutableList<NavigationTab> = persistentListOf(
        NavigationTab.A,
        NavigationTab.B,
        NavigationTab.C,
    ),
    val selectedTab: NavigationTab = NavigationTab.A,
) : ViewState
