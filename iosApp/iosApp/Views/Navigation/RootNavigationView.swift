import KMP
import SwiftUI

struct RootNavigationView: View {
    @State private var slot: StateFlowObserver<ChildSlot<RootConfig, RootChild>>

    private let openDeepLink: (String) -> Void

    init(_ component: RootNavHost) {
        _slot = State(wrappedValue: StateFlowObserver(component.slot))
        openDeepLink = component.actions.onDeepLink
    }

    var body: some View {
        ZStack {
            if let navigationEntry = slot.value.child?.instance {
                switch onEnum(of: navigationEntry) {
                case let .login(entry):
                    LoginComponent(model: LoginComponentModel(entry.screen)).id(entry.iosViewId)
                case let .signedIn(entry):
                    SignedInNavigationView(entry.navHost).id(entry.iosViewId)
                }
            }
        }
        .onOpenURL { url in
            openDeepLink(url.absoluteString)
        }
    }
}
