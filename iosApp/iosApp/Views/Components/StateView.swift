import KMP
import SwiftUI

enum StateViewState {
    case alert(dialog: UiDialog, retryAction: (() -> Void)?)
    case error(error: UiError, retryAction: (() -> Void)?)
    case content

    init(dialog: UiDialog?, error: UiError?, retryAction: (() -> Void)?) {
        if let dialog {
            self = .alert(dialog: dialog, retryAction: retryAction)
        } else if let error {
            self = .error(error: error, retryAction: retryAction)
        } else {
            self = .content
        }
    }
}

struct StateView<Content: View>: View {
    @State private var isPresented: Bool = false
    let state: StateViewState
    let content: () -> Content

    init(
        state: StateViewState,
        @ViewBuilder content: @escaping () -> Content
    ) {
        self.state = state
        self.content = content
    }

    var body: some View {
        if let error {
            ZStack {
                BluredColorsView(shouldOffest: false)
                VStack(spacing: 10) {
                    Text(error.title.localized())
                        .appFont(.heading1)
                        .multilineTextAlignment(.center)
                    if let message = error.message {
                        Text(message.localized())
                            .appFont(.description3)
                            .multilineTextAlignment(.center)
                    }
                    if let retry = retryAction {
                        AppButton(
                            text: Localizable.try_again.localized,
                            action: retry
                        )
                        .padding(.top, 50)
                    }
                }
                .padding(.horizontal, 20)
            }
            .ignoresSafeArea()
            .frame(width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height, alignment: .center)
        } else {
            content()
                .errorAlert(isPresented: $isPresented, dialog: dialog)
                .onChange(of: dialog) { dialog in
                    isPresented = dialog != nil
                }
        }
    }

    var dialog: UiDialog? {
        if case let .alert(dialog, _) = state {
            return dialog
        }
        return nil
    }

    var error: UiError? {
        if case let .error(error, _) = state {
            return error
        }
        return nil
    }

    var retryAction: (() -> Void)? {
        switch state {
        case let .alert(_, retryAction):
            return retryAction
        case let .error(_, retryAction):
            return retryAction
        case .content:
            return nil
        }
    }
}
