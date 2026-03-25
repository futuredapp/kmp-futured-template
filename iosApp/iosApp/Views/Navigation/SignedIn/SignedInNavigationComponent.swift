import KMP
import SwiftUI

struct SignedInNavigationComponent<Model: SignedInNavigationComponentModelProtocol>: View {
    @State var model: Model

    var body: some View {
        TabView(selection: $model.selectedTab) {
            TabContentView(ofNavigationEntry: model.homeTab, forNavigationTab: NavigationTab.home) { child in
                HomeTabNavigationComponent(model: HomeTabNavigationComponentModel(child.navHost))
            }
            TabContentView(ofNavigationEntry: model.profileTab, forNavigationTab: NavigationTab.profile) { child in
                ProfileTabNavigationComponent(model: ProfileTabNavigationComponentModel(child.navHost))
            }
        }
    }
}
