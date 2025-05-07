import KMP
import SwiftUI

struct FirstMultiplatformComposeView<FirstScreen: KMP.FirstScreen>: UIViewControllerRepresentable {

    private let screen: FirstScreen

    init(_ screen: FirstScreen) {
        self.screen = screen
    }

    func makeUIViewController(context: Context) -> UIViewController {
        ComposeMultiplatformFirstScreenControllerKt.ComposeMultiplatformFirstScreenController(screen: screen)
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct FirstMultiplatformView<ViewModel: FirstViewModelProtocol>: View {
    private let viewModel: ViewModel

    init(_ viewModel: ViewModel) {
        self.viewModel = viewModel
    }
    var body: some View {
        FirstMultiplatformComposeView(viewModel.screen)
                .ignoresSafeArea(.all) // we will use compose Window Insets API
                .ignoresSafeArea(.keyboard)  // Compose has own keyboard handler
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
