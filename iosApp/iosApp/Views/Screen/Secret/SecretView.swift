import shared
import SwiftUI

struct SecretView<ViewModel: SecretViewModelProtocol>: View {
    private let viewModel: ViewModel

    init(_ viewModel: ViewModel) {
        self.viewModel = viewModel
    }

    var body: some View {
        VStack(spacing: 10) {
            Text(viewModel.text)
            Button("Go back", action: viewModel.goBack)
        }
        .navigationTitle(Localizable.secret_screen_title.localized)
    }
}
