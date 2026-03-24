import KMP
import SwiftUI

struct ProfileTabNavigationComponent: View {
    @State var model: ProfileTabNavigationComponentModel

    var body: some View {
        DecomposeNavigationStack(
            kotlinStack: model.stack,
            setPath: model.actions.navigate
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
