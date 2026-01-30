import KMP
import SwiftUI

struct FirstView<ViewModel: FirstViewModelProtocol>: View {
    private let viewModel: ViewModel

    init(_ viewModel: ViewModel) {
        self.viewModel = viewModel
    }

    var body: some View {
        VStack(spacing: 10) {
            Text(viewModel.counter)
            Text(viewModel.createdAt)
            if let randomPerson = viewModel.randomPerson {
                Text(randomPerson).multilineTextAlignment(.center)
            }
            Button(Localizable.first_screen_button.localized, action: viewModel.onNext).buttonStyle(.borderedProminent)
        }
        .navigationTitle(Localizable.first_screen_title.localized)
        .alert(viewModel.alertText, isPresented: viewModel.isAlertVisible) {
            Button(Localizable.generic_close.localized) { viewModel.hideToast() }
        }
    }
}

#if DEBUG
private struct FirstViewPreviewViewModel: FirstViewModelProtocol {
    var counter: String { "42" }
    var createdAt: String { "2026-01-29" }
    var randomPerson: String? { "Ada Lovelace\nGrace Hopper\nAlan Turing" }

    var isAlertVisible: Binding<Bool> { .constant(false) }
    var alertText: String { "" }

    func onNext() {}
    func showToast(event: FirstUiEvent.ShowToast) {}
    func hideToast() {}
}

#Preview("FirstView") {
    NavigationStack {
        FirstView(FirstViewPreviewViewModel())
    }
}
#endif
