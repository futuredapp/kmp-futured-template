package app.futured.kmptemplate.feature_v3.navigation.root

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class RootNavHostState(
    val navigationTabs: ImmutableList<NavigationTab> = persistentListOf(
        NavigationTab.HOME,
        NavigationTab.PROFILE,
    ),
    val selectedTab: NavigationTab = NavigationTab.HOME,
)
