import KMP
import SwiftUI

struct LoginComposeView: UIViewControllerRepresentable {
    private let screen: LoginScreen
    init(_ screen: LoginScreen) { self.screen = screen }
    func makeUIViewController(context: Context) -> some UIViewController {
        LoginUiController(screen: screen)
    }
    func updateUIViewController(_ uiViewController: UIViewControllerType, context: Context) {}
}

struct LoginView: View {
    private let screen: LoginScreen
    init(_ screen: LoginScreen) { self.screen = screen }
    var body: some View {
        LoginComposeView(screen).ignoresSafeArea()
    }
}
