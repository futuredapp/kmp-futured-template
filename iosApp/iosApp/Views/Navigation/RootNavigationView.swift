import shared
import SwiftUI

struct RootNavigationView: View {

    @StateObject @KotlinStateFlow private var slot: ChildSlot<RootDestination, RootEntry>
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
                    LoginView(LoginViewModel(entry.instance))
                case .signedIn(let entry):
                    SignedInNavigationView(entry.instance)
                }
            }
        }
        .onOpenURL { url in
            openDeepLink(url.absoluteString)
        }
    }
}
