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
            if let tabANavEntry = tabA {
                TabANavigationView(tabANavEntry.instance)
                    .tag(SignedInNavigationViewState.Tab.a)
                    .tabItem {
                        Label {
                            Text("Tab A")
                        } icon: {
                            Image(systemName: "house.fill")
                                .renderingMode(.template)
                        }
                    }
            }

            if let tabBNavEntry = tabB {
                TabBNavigationView(tabBNavEntry.instance)
                    .tag(SignedInNavigationViewState.Tab.b)
                    .tabItem {
                        Label {
                            Text("Tab B")
                        } icon: {
                            Image(systemName: "plus")
                                .renderingMode(.template)
                        }
                    }
            }

            if let tabCNavEntry = tabC {
                TabCNavigationView(tabCNavEntry.instance)
                    .tag(SignedInNavigationViewState.Tab.c)
                    .tabItem {
                        Label {
                            Text("Tab C")
                        } icon: {
                            Image(systemName: "person.crop.circle")
                                .renderingMode(.template)
                        }
                    }
            }
        }
    }
}
