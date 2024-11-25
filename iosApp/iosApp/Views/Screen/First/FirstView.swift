import shared
import SwiftUI

struct FirstView<ViewModel: FirstViewModelProtocol>: View {
    private let viewModel: ViewModel

    init(_ viewModel: ViewModel) {
        self.viewModel = viewModel
    }

    var body: some View {
        VStack(spacing: 10) {
            Text(viewModel.text)
            Button("Go to second screen", action: viewModel.onNext).buttonStyle(.borderedProminent)
        }
        .navigationTitle("First screen")
        .eventsEffect(for: viewModel.events) { event in
            switch onEnum(of: event) {
            case .showToast(let event):
                viewModel.showToast(event: event)
            }
        }
        .alert(viewModel.alertText, isPresented: viewModel.isAlertVisible) {
            Button("Close") { viewModel.hideToast() }
        }
    }
}
