import KMP
import SwiftUI

private struct ErrorAlertModifier: ViewModifier {
    @Binding var isPresented: Bool
    let dialog: UiDialog?

    func body(content: Content) -> some View {
        content
            .alert(dialog?.title.localized() ?? "", isPresented: _isPresented) {
                if let primaryButton = dialog?.primaryButton {
                    Button(action: primaryButton.action) {
                        Text(primaryButton.title.localized())
                    }
                }
                if let dismissButton = dialog?.dismissButton {
                    Button(action: dismissButton.action) {
                        Text(dismissButton.title.localized())
                    }
                }
            } message: {
                if let text = dialog?.text {
                    Text(text.localized())
                }
            }
    }
}

extension View {
    func errorAlert(isPresented: Binding<Bool>, dialog: UiDialog?) -> some View {
        modifier(ErrorAlertModifier(isPresented: isPresented, dialog: dialog))
    }
}
