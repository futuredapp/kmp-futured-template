import shared
import SwiftUI

struct ProfileView<ViewModel: ProfileViewModelProtocol>: View {
    private let viewModel: ViewModel

    init(_ viewModel: ViewModel) {
        self.viewModel = viewModel
    }

    var body: some View {
        VStack(spacing: 10) {
            Text("Profile")
            Button("Sign Out", action: viewModel.onLogoutClick)
        }
    }
}
