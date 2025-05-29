import KMP
import SwiftUI

protocol FormViewModelProtocol: DynamicProperty {
    var screen: FormScreen { get }
    var alertTitle: String? { get }
    var alertMessage: String { get }
    var alertPositiveButtonText: String { get }
    var alertNegativeButtonText: String? { get }
    var isAlertPresent: Binding<Bool> { get }

    func showAlertDialogAction()
    var positiveAction: () -> Void { get }
    var negativeAction: () -> Void? { get }
}

struct FormViewModel {
    @StateObject @KotlinStateFlow private var viewState: FormViewState
    let screen: FormScreen
    private let actions: FormScreenActions

    init(_ screen: FormScreen) {
        _viewState = .init(screen.viewState)
        self.screen = screen
        self.actions = screen.actions
    }
}

extension FormViewModel: FormViewModelProtocol {

    var alertTitle: String? {
        viewState.alertDialogUi?.title?.localized()
    }

    var alertMessage: String {
        viewState.alertDialogUi?.message.localized() ?? ""
    }

    var alertPositiveButtonText: String {
        viewState.alertDialogUi?.positiveButton.localized() ?? ""
    }

    var alertNegativeButtonText: String? {
        viewState.alertDialogUi?.dismissButton?.localized()
    }

    var positiveAction: () -> Void {
        { viewState.alertDialogUi?.onPositiveClick() }
    }

    var negativeAction: () -> Void? {
        { viewState.alertDialogUi?.onDismiss() }
    }

    var isAlertPresent: Binding<Bool> {
        Binding(
            get: { viewState.alertDialogUi != nil },
            set: { _ in // Explicitly name the argument (or use _)
                // You can optionally use newValue if needed, e.g.,
                // if newValue is false {
                //     viewState.alertDialogUi?.onDismiss()
                // }
                // For your current case, simply calling onDismiss is likely what you want
                // regardless of whether it's being set to true or false by the UI,
                // as setting it to false often means dismissal.
                viewState.alertDialogUi?.onDismiss()
            }
        )
    }

    func showAlertDialogAction() {
        actions.onShowAlertDialog()
    }
}
