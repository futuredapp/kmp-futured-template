import shared
import SwiftUI

struct SecondView<ViewModel: SecondViewModelProtocol>: View {
    private let viewModel: ViewModel

    init(_ viewModel: ViewModel) {
        self.viewModel = viewModel
    }

    var body: some View {
        VStack(spacing: 10) {
            Text(viewModel.text)
            Button("Go to third screen", action: viewModel.onNext).buttonStyle(.borderedProminent)
            Button("Go back", action: viewModel.onBack)
        }
        .navigationTitle(Localizable.second_screen_title.localized)
    }
}
