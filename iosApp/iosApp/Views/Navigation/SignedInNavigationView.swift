import KMP
import SwiftUI

struct SignedInNavigationView: View {
    @State private var homeTab: OptionalStateFlowObserver<SignedInChildHome>
    @State private var profileTab: OptionalStateFlowObserver<SignedInChildProfile>
    @State private var viewState: StateFlowObserver<SignedInNavHostViewState>

    private let actions: SignedInNavHostActions

    init(_ component: SignedInNavHost) {
        _homeTab = State(wrappedValue: OptionalStateFlowObserver(component.homeTab))
        _profileTab = State(wrappedValue: OptionalStateFlowObserver(component.profileTab))
        _viewState = State(wrappedValue: StateFlowObserver(component.viewState))
        actions = component.actions
    }

    var body: some View {
        tabBar
    }

    private var tabBar: some View {
        TabView(
            selection: Binding(
                get: { viewState.value.selectedTab },
                set: {
                    actions.onTabSelected(tab: $0)
                }
            )
        ) {
            TabContentView(ofNavigationEntry: homeTab.value, forNavigationTab: NavigationTab.home) { child in
                HomeTabNavigationView(child.navHost)
            }
            TabContentView(ofNavigationEntry: profileTab.value, forNavigationTab: NavigationTab.profile) { child in
                ProfileTabNavigationView(child.navHost)
            }
        }
    }
}
