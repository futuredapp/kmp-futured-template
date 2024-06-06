import shared
import SwiftUI

protocol LoginViewModelProtocol: DynamicProperty {
    func onLoginClick()
    func onRefresh() async
}

struct LoginViewModel {
    @StateObject @KotlinStateFlow private var viewState: LoginViewState
    private let actions: LoginScreenActions
    private let suspendActions: LoginScreenSuspendActions

    init(_ screen: LoginScreen) {
        _viewState = .init(screen.viewState)
        actions = screen.actions
        suspendActions = screen.suspendActions
    }
}

extension LoginViewModel: LoginViewModelProtocol {
    func onLoginClick() {
        actions.onLoginClick()
    }

    func onRefresh() async {
        try? await suspendActions.refresh()
    }
}
