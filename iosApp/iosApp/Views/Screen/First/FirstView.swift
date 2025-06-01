import KMP
import SwiftUI

struct FirstView<ViewModel: FirstViewModelProtocol>: View {
    private let viewModel: ViewModel

    init(_ viewModel: ViewModel) {
        self.viewModel = viewModel
    }

    var body: some View {
        VStack(spacing: 10) {
        }
        .eventsEffect(for: viewModel.events) { event in
            switch onEnum(of: event) {
            case .openSystemSettings(let event):
                UIApplication.shared.open(URL(string: UIApplication.openSettingsURLString)!)
            }
        }
        .alert(viewModel.alertText, isPresented: viewModel.isAlertVisible) {
        }
    }
}
