import KMP
import SwiftUI

struct FirstView<ViewModel: FirstViewModelProtocol>: View {
    private let viewModel: ViewModel

    init(_ viewModel: ViewModel) {
        self.viewModel = viewModel
    }

    var body: some View {
        VStack(spacing: 10) {
            Text(viewModel.text)
            Button(Localizable.first_screen_button.localized, action: viewModel.onNext).buttonStyle(.borderedProminent)
            Button(Localizable.generic_switch_to_multiplatform.localized, action: viewModel.switchToComposeMultiplatform).buttonStyle(.borderedProminent)
            if let randomPerson = viewModel.randomPerson {
                Text(randomPerson).multilineTextAlignment(.center)
            }
        }
        .navigationTitle(Localizable.first_screen_title.localized)
        .eventsEffect(for: viewModel.events) { event in
            switch onEnum(of: event) {
            case .showToast(let event):
                viewModel.showToast(event: event)
            }
        }
        .alert(viewModel.alertText, isPresented: viewModel.isAlertVisible) {
            Button(Localizable.generic_close.localized) { viewModel.hideToast() }
        }
    }
}
