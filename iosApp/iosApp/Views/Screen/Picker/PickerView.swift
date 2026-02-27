import KMP
import SwiftUI

struct PickerComposeView: UIViewControllerRepresentable {
    private let screen: PickerScreen
    init(_ screen: PickerScreen) { self.screen = screen }
    func makeUIViewController(context: Context) -> some UIViewController {
        PickerUiController(screen: screen)
    }
    func updateUIViewController(_ uiViewController: UIViewControllerType, context: Context) {}
}

struct PickerView: View {
    private let screen: PickerScreen
    init(_ screen: PickerScreen) { self.screen = screen }
    var body: some View {
        PickerComposeView(screen).ignoresSafeArea()
    }
}
