import shared
import SwiftUI

struct ThirdView<ViewModel: ThirdViewModelProtocol>: View {
    private let viewModel: ViewModel

    init(_ viewModel: ViewModel) {
        self.viewModel = viewModel
    }

    var body: some View {
        VStack(spacing: 10) {
            Text(viewModel.text)
            Button("Go back", action: viewModel.onBack)
        }
        .navigationTitle(Localizable.third_screen_text.localized)
    }
}
