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
            ZStack {
                if let tabANavEntry = tabA {
                    // The .id() modifier is very important, tells view to render again whenever underlying navEntry is updated.
                    // I literally spent entire weekend figuring this one out 🔫.
                    TabANavigationView(tabANavEntry.instance).id(tabANavEntry.iosViewId)
                }
            }
            .tag(NavigationTab.a)
            .tabItem {
                Label {
                    Text(NavigationTab.a.title.localized())
                } icon: {
                    Image(systemName: "house.fill")
                        .renderingMode(.template)
                }
            }

            ZStack {
                if let tabBNavEntry = tabB {
                    TabBNavigationView(tabBNavEntry.instance).id(tabBNavEntry.iosViewId)
                }
            }
            .tag(NavigationTab.b)
            .tabItem {
                Label {
                    Text(NavigationTab.b.title.localized())
                } icon: {
                    Image(systemName: "plus")
                        .renderingMode(.template)
                }
            }

            ZStack {
                if let tabCNavEntry = tabC {
                    TabCNavigationView(tabCNavEntry.instance).id(tabCNavEntry.iosViewId)
                }
            }
            .tag(NavigationTab.c)
            .tabItem {
                Label {
                    Text(NavigationTab.c.title.localized())
                } icon: {
                    Image(systemName: "person.crop.circle")
                        .renderingMode(.template)
                }
            }
        }
    }
}
