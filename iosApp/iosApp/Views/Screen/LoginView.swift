import SwiftUI
import shared

struct LoginView: View {
    
    @ObservedObject @KotlinState private var viewState: LoginViewState
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
                Text("Hi from login. Try to refresh me.")
                Button("Login", action: actions.onLoginClick).buttonStyle(.borderedProminent)
            }.scaledToFill()
        }
        .refreshable {
            try? await suspendActions.refresh()
        }
    }
}
