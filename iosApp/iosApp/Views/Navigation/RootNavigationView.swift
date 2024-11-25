import shared
import SwiftUI

struct RootNavigationView: View {

    @StateObject @KotlinStateFlow private var slot: ChildSlot<RootConfig, RootChild>
    private let openDeepLink: (String) -> Void

    init(_ component: RootNavHost) {
        self._slot = .init(component.slot)
        self.openDeepLink = component.actions.onDeepLink
    }

    var body: some View {
        ZStack {
            if let navigationEntry = slot.child?.instance {
                switch onEnum(of: navigationEntry) {
                case .login(let entry):
                    LoginView(LoginViewModel(entry.screen))
                case .signedIn(let entry):
                    SignedInNavigationView(entry.navHost)
                }
            }
        }
        .onOpenURL { url in
            openDeepLink(url.absoluteString)
        }
    }
}
