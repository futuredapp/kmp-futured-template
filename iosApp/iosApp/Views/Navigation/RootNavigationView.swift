import SwiftUI
import shared

struct RootNavigationView: View {
    
    @ObservedObject @KotlinState private var slot: ChildSlot<RootDestination, RootNavigationEntry>
    
    init(_ component: RootNavigation) {
        self._slot = .init(component.slot)
    }
    
    var body: some View {
        if let navigationEntry = slot.child?.instance {
            switch onEnum(of: navigationEntry) {
            case .login(let entry):
                LoginView(entry.screen)
            case .home(let entry):
                HomeNavigationView(entry.navigation)
            }
        }
    }
}
