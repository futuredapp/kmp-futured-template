import KMP
import SwiftUI

struct SecondComposeView: UIViewControllerRepresentable {
    private let screen: SecondScreen
    init(_ screen: SecondScreen) { self.screen = screen }
    func makeUIViewController(context: Context) -> some UIViewController {
        SecondUiController(screen: screen)
    }
    func updateUIViewController(_ uiViewController: UIViewControllerType, context: Context) {}
}

struct SecondView: View {
    private let screen: SecondScreen
    init(_ screen: SecondScreen) { self.screen = screen }
    var body: some View {
        SecondComposeView(screen).ignoresSafeArea()
    }
}
