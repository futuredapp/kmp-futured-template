import KMP
import SwiftUI

struct FirstComposeView: UIViewControllerRepresentable {
    private let screen: FirstScreen
    init(_ screen: FirstScreen) { self.screen = screen }
    func makeUIViewController(context: Context) -> some UIViewController {
        FirstUiController(screen: screen)
    }
    func updateUIViewController(_ uiViewController: UIViewControllerType, context: Context) {}
}

struct FirstView: View {
    private let screen: FirstScreen
    init(_ screen: FirstScreen) { self.screen = screen }
    var body: some View {
        FirstComposeView(screen).ignoresSafeArea()
    }
}
