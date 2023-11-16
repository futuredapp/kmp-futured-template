import SwiftUI
import shared

struct RootNavigationView: View {
    
    @ObservedObject @KotlinStateFlow private var slot: ChildSlot<RootDestination, RootEntry>
    
    init(_ component: RootNavigation) {
        self._slot = .init(component.slot)
    }
    
    var body: some View {
        ZStack {
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
}
