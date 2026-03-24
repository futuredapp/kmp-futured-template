import KMP
import SwiftUI

struct ProfileTabNavigationComponent<Model: ProfileTabNavigationComponentModelProtocol>: View {
    @State var model: Model

    var body: some View {
        DecomposeNavigationStack(
            kotlinStack: model.stack,
            setPath: model.navigate
        ) { child in
            switch onEnum(of: child) {
            case let .profile(entry):
                ProfileComponent(model: ProfileComponentModel(entry.screen))
            case let .third(entry):
                ThirdComponent(model: ThirdComponentModel(entry.screen))
            }
        }
    }
}
