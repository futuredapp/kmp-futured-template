import KMP
import SwiftUI

struct SignedInNavigationComponent: View {
    @State var model: SignedInNavigationComponentModel

    var body: some View {
        TabView(
            selection: Binding(
                get: { model.selectedTab },
                set: { model.onTabSelected($0) }
            )
        ) {
            TabContentView(ofNavigationEntry: model.homeTab, forNavigationTab: NavigationTab.home) { child in
                HomeTabNavigationComponent(model: HomeTabNavigationComponentModel(child.navHost))
            }
            TabContentView(ofNavigationEntry: model.profileTab, forNavigationTab: NavigationTab.profile) { child in
                ProfileTabNavigationComponent(model: ProfileTabNavigationComponentModel(child.navHost))
            }
        }
    }
}
