import KMP
import SwiftUI

struct ThirdComposeView: UIViewControllerRepresentable {
    private let screen: ThirdScreen
    init(_ screen: ThirdScreen) { self.screen = screen }
    func makeUIViewController(context: Context) -> some UIViewController {
        ThirdUiController(screen: screen)
    }
    func updateUIViewController(_ uiViewController: UIViewControllerType, context: Context) {}
}

struct ThirdView: View {
    private let screen: ThirdScreen
    init(_ screen: ThirdScreen) { self.screen = screen }
    var body: some View {
        ThirdComposeView(screen).ignoresSafeArea()
    }
}
