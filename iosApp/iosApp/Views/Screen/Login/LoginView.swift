import shared
import SwiftUI

struct LoginView<ViewModel: LoginViewModelProtocol>: View {
    private let viewModel: ViewModel

    init(_ viewModel: ViewModel) {
        self.viewModel = viewModel
    }

    var body: some View {
        VStack(spacing: 10) {
            Text("Welcome to the sample app")
            Button("Sign In", action: viewModel.onLoginClick).buttonStyle(.borderedProminent)
        }
    }
}
