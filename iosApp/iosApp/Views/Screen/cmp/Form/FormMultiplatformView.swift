import KMP
import SwiftUI

struct FormMultiplatformComposeView<FormScreen: KMP.FormScreen>: UIViewControllerRepresentable {

    private let screen: FormScreen

    init(_ screen: FormScreen) {
        self.screen = screen
    }

    func makeUIViewController(context: Context) -> UIViewController {
        ComposeMultiplatformFormScreenControllerKt.ComposeMultiplatformFormScreenController(screen: screen)
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct FormMultiplatformView<ViewModel: FormViewModelProtocol>: View {
    private let viewModel: ViewModel

    init(_ viewModel: ViewModel) {
        self.viewModel = viewModel
    }
    var body: some View {
        FormMultiplatformComposeView(viewModel.screen)
                .ignoresSafeArea(.all) // we will use compose Window Insets API
    }
}
