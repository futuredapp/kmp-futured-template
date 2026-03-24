import KMP
import SwiftUI

struct RootNavigationComponent<Model: RootNavigationComponentModelProtocol>: View {
    @State var model: Model

    var body: some View {
        Group {
            if let navigationEntry = model.slotChild {
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
