import KMP
import SwiftUI

struct RootNavigationComponent: View {
    @State var model: RootNavigationComponentModel

    var body: some View {
        Group {
            if let navigationEntry = model.slot.child?.instance {
                switch onEnum(of: navigationEntry) {
                case let .login(entry):
                    LoginComponent(model: LoginComponentModel(entry.screen)).id(entry.iosViewId)
                case let .signedIn(entry):
                    SignedInNavigationComponent(model: SignedInNavigationComponentModel(entry.navHost)).id(entry.iosViewId)
                }
            }
        }
        .onOpenURL { url in
            model.onDeepLink(url.absoluteString)
        }
    }
}
