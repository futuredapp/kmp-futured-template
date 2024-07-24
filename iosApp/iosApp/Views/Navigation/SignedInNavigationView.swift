import shared
import SwiftUI

struct SignedInNavigationView: View {

    private let actions: SignedInNavigationActions

    @StateObject @KotlinOptionalStateFlow private var tabA: SignedInNavEntry.A?
    @StateObject @KotlinOptionalStateFlow private var tabB: SignedInNavEntry.B?
    @StateObject @KotlinOptionalStateFlow private var tabC: SignedInNavEntry.C?

    @StateObject @KotlinStateFlow private var viewState: SignedInNavigationViewState

    init(_ component: SignedInNavigation) {
        self._tabA = .init(component.tabA)
        self._tabB = .init(component.tabB)
        self._tabC = .init(component.tabC)
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
            TabContentView(ofNavigationEntry: tabA, forNavigationTab: NavigationTab.a) { entry in
                TabANavigationView(entry.instance)
            }

            TabContentView(ofNavigationEntry: tabB, forNavigationTab: NavigationTab.b) { entry in
                TabBNavigationView(entry.instance)
            }

            TabContentView(ofNavigationEntry: tabC, forNavigationTab: NavigationTab.c) { entry in
                TabCNavigationView(entry.instance)
            }
        }
    }
}
