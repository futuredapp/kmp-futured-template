import KMP
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
                    LoginView(LoginViewModel(entry.screen)).id(entry.iosViewId)
                case .signedIn(let entry):
                    SignedInNavigationView(entry.navHost).id(entry.iosViewId)
                }
            }
        }
        .onOpenURL { url in
            openDeepLink(url.absoluteString)
        }
    }
}
