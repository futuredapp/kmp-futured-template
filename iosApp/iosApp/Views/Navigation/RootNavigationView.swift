import shared
import SwiftUI

struct RootNavigationView: View {

    @ObservedObject @KotlinStateFlow private var slot: ChildSlot<RootDestination, RootEntry>
    private let openDeepLink: (String) -> Void

    init(_ component: RootNavigation) {
        self._slot = .init(component.slot)
        self.openDeepLink = component.openDeepLink
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
        .onOpenURL { url in
            openDeepLink(url.absoluteString)
        }
    }
}
