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

struct FirstMultiplatformView<FirstScreen: KMP.FirstScreen>: View {
    private let screen: FirstScreen

    init(_ screen: FirstScreen) {
        self.screen = screen
    }
    var body: some View {
        FirstMultiplatformComposeView(screen)
                .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
    }
}
