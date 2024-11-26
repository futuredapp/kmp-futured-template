import shared
import SwiftUI

struct ProfileView<ViewModel: ProfileViewModelProtocol>: View {
    private let viewModel: ViewModel

    init(_ viewModel: ViewModel) {
        self.viewModel = viewModel
    }

    var body: some View {
        VStack(spacing: 10) {
            Text(Localizable.login_screen_title.localized)
            Button(Localizable.generic_sign_out.localized, action: viewModel.onLogoutClick)
        }
    }
}
