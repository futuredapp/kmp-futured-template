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
            HStack {
                Button("Pick a fruit", action: viewModel.onPickFruit).buttonStyle(.bordered)
                Button("Pick a veggie", action: viewModel.onPickVeggie).buttonStyle(.bordered)
            }
        }
        .navigationTitle(Localizable.second_screen_title.localized)
        .sheet(
            isPresented: Binding(
                get: {
                    viewModel.picker != nil
                },
                set: { _ in
                    viewModel.onPickerDismissed()
                }
            )
        ) {
            if let picker = viewModel.picker {
                PickerView(PickerViewModel(picker)).presentationDetents([.medium])
            }
        }
    }
}
