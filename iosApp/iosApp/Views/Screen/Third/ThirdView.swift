import KMP
import SwiftUI

struct ThirdView<ViewModel: ThirdViewModelProtocol>: View {
    private let viewModel: ViewModel

    init(_ viewModel: ViewModel) {
        self.viewModel = viewModel
    }

    var body: some View {
        VStack(spacing: 10) {
            Text(viewModel.text)
        }
        .navigationTitle(Localizable.third_screen_title.localized)
    }
}
