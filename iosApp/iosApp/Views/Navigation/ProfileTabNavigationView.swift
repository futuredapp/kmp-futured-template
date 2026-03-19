import KMP
import SwiftUI

struct ProfileTabNavigationView: View {
    private let stack: SkieSwiftStateFlow<ChildStack<ProfileConfig, ProfileChild>>
    private let actions: ProfileNavHostActions

    init(_ component: ProfileNavHost) {
        stack = component.stack
        actions = component.actions
    }

    var body: some View {
        DecomposeNavigationStack(
            kotlinStack: stack,
            setPath: actions.navigate
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
