import KMP
import SwiftUI

struct SignedInNavigationView: View {

    private let actions: SignedInNavHostActions

    @StateObject @KotlinOptionalStateFlow private var homeTab: SignedInChildHome?
    @StateObject @KotlinOptionalStateFlow private var profileTab: SignedInChildProfile?
    @StateObject @KotlinOptionalStateFlow private var cmpTab: SignedInChildCmp?

    @StateObject @KotlinStateFlow private var viewState: SignedInNavHostViewState

    init(_ component: SignedInNavHost) {
        self._homeTab = .init(component.homeTab)
        self._profileTab = .init(component.profileTab)
        self._cmpTab = .init(component.cmpTab)
        self._viewState = .init(component.viewState)
        self.actions = component.actions
    }

    var body: some View {
        tabBar
    }

    private var tabBar: some View {
        TabView(
            selection: Binding(
                get: { viewState.selectedTab },
                set: { actions.onTabSelected(tab: $0 ) }
            )
        ) {
            TabContentView(ofNavigationEntry: homeTab, forNavigationTab: NavigationTab.home) { child in
                HomeTabNavigationView(child.navHost)
            }
            TabContentView(ofNavigationEntry: profileTab, forNavigationTab: NavigationTab.profile) { child in
                ProfileTabNavigationView(child.navHost)
            }
            TabContentView(ofNavigationEntry: cmpTab, forNavigationTab: NavigationTab.cmp) { child in
                CmpTabNavigationView(child.navHost)
            }
        }
    }
}
