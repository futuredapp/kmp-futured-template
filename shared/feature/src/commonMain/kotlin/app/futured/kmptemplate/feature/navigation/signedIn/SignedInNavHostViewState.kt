package app.futured.kmptemplate.feature.navigation.signedIn

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class SignedInNavHostViewState(
    val navigationTabs: ImmutableList<NavigationTab> = persistentListOf(
        NavigationTab.HOME,
        NavigationTab.PROFILE,
    ),
    val selectedTab: NavigationTab = NavigationTab.HOME,
)
