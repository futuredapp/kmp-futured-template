import shared
import SwiftUI

struct LoginView: View {

    @ObservedObject @KotlinStateFlow private var viewState: LoginViewState
    private let actions: LoginScreenActions
    private let suspendActions: LoginScreenSuspendActions

    init(_ screen: LoginScreen) {
        self._viewState = .init(screen.viewState)
        self.actions = screen.actions
        self.suspendActions = screen.suspendActions
    }

    var body: some View {
        ScrollView {
            VStack(spacing: 10) {
                Spacer(minLength: 20.0)
                Text(Localizable.login_screen_text.localized)
                Button("Login", action: actions.onLoginClick).buttonStyle(.borderedProminent)
            }.scaledToFill()
        }
        .refreshable {
            try? await suspendActions.refresh()
        }
    }
}
