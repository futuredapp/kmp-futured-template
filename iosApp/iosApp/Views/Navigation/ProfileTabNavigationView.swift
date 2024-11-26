import shared
import SwiftUI

struct ProfileTabNavigationView: View {

    private let stack: SkieSwiftStateFlow<ChildStack<ProfileConfig, ProfileChild>>
    private let actions: ProfileNavHostActions

    // TODO profile tab

    init(_ component: ProfileNavHost) {
        self.stack = component.stack
        self.actions = component.actions
    }

    var body: some View {
        DecomposeNavigationStack(
            kotlinStack: stack,
            setPath: actions.navigate
        ) { child in
            switch onEnum(of: child) {
            case .profile(let entry):
                ProfileView(ProfileViewModel(entry.screen))
            }
        }
    }
}
