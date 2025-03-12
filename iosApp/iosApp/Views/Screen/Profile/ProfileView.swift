import shared
import SwiftUI

struct ProfileView<ViewModel: ProfileViewModelProtocol>: View {
    private let viewModel: ViewModel

    init(_ viewModel: ViewModel) {
        self.viewModel = viewModel
    }

    var body: some View {
        VStack(spacing: 10) {
            Button(Localizable.generic_sign_out.localized, action: viewModel.onLogoutClick)
            Button(Localizable.profile_navigate_to_third.localized, action: viewModel.onThirdClick)
        }
        .navigationTitle(Localizable.profile_screen_title.localized)
    }
}
