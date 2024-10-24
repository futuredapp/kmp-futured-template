import shared
import SwiftUI

struct LoginView<ViewModel: LoginViewModelProtocol>: View {
    private let viewModel: ViewModel

    init(_ viewModel: ViewModel) {
        self.viewModel = viewModel
    }

    var body: some View {
        ScrollView {
            VStack(spacing: 10) {
                Spacer(minLength: 20.0)
                Text(Localizable.login_screen_text.localized)
                Button("Login", action: viewModel.onLoginClick).buttonStyle(.borderedProminent)
            }.scaledToFill()
        }
        .refreshable {
            await viewModel.onRefresh()
        }
        .task {
            await model.onAppear()
        }
    }
}
