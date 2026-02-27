import KMP
import SwiftUI

struct ProfileComposeView: UIViewControllerRepresentable {
    private let screen: ProfileScreen
    init(_ screen: ProfileScreen) { self.screen = screen }
    func makeUIViewController(context: Context) -> some UIViewController {
        ProfileUiController(screen: screen)
    }
    func updateUIViewController(_ uiViewController: UIViewControllerType, context: Context) {}
}

struct ProfileView: View {
    private let screen: ProfileScreen
    init(_ screen: ProfileScreen) { self.screen = screen }
    var body: some View {
        ProfileComposeView(screen).ignoresSafeArea()
    }
}
