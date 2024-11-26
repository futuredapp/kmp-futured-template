import shared
import SwiftUI

struct LoginView<ViewModel: LoginViewModelProtocol>: View {
    private let viewModel: ViewModel

    init(_ viewModel: ViewModel) {
        self.viewModel = viewModel
    }

    var body: some View {
        VStack(spacing: 10) {
            Text(Localizable.login_screen_title.localized)
            Button(Localizable.generic_sign_in.localized, action: viewModel.onLoginClick).buttonStyle(.borderedProminent)
        }
    }
}
