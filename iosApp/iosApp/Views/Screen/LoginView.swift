import SwiftUI
import shared

struct LoginView: View {
    
    @ObservedObject @KotlinState private var viewState: LoginViewState
    private let actions: LoginScreenActions
    
    init(_ screen: LoginScreen) {
        self._viewState = .init(screen.viewState)
        self.actions = screen.actions
    }
    
    var body: some View {
        VStack(spacing: 10) {
            Text("Hi from login")
            Button("Login", action: actions.onLoginClick).buttonStyle(.borderedProminent)
        }
    }
}
