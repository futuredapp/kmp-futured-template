import KMP
import SwiftUI

struct SecondView<ViewModel: SecondViewModelProtocol>: View {
    private let viewModel: ViewModel

    init(_ viewModel: ViewModel) {
        self.viewModel = viewModel
    }

    var body: some View {
        VStack {
            Text(viewModel.createdAt)
            HStack {
                Button(Localizable.second_screen_button_fruit.localized, action: viewModel.onPickFruit).buttonStyle(.bordered)
                Button(Localizable.second_screen_button_veggie.localized, action: viewModel.onPickVeggie).buttonStyle(.bordered)
            }
        }
        .navigationTitle(Localizable.second_screen_title.localized)
    }
}
