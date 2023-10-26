import SwiftUI
import shared

struct RootNavigationView: View {
    
    @ObservedObject @KotlinState private var slot: ChildSlot<RootDestination, RootNavigationEntry>
    
    init(_ component: RootNavigation) {
        self._slot = .init(component.slot)
    }
    
    var body: some View {
        if let navigationEntry = slot.child?.instance {
            switch(RootNavigationEntryKs(navigationEntry)) {
            case .login(let entry):
                LoginView(entry.screen)
            case .home(let entry):
                HomeNavigationView(entry.navigation)
            }
        }
    }
}

private enum RootNavigationEntryKs {
    
    case login(RootNavigationEntry.Login)
    case home(RootNavigationEntry.Home)
    
    public init(_ obj: RootNavigationEntry) {
        if let obj = obj as? shared.RootNavigationEntry.Login {
            self = .login(obj)
        } else if let obj = obj as? shared.RootNavigationEntry.Home {
            self = .home(obj)
        } else {
            fatalError("Invalid enum value. Replace with SKIE.")
        }
    }
}
