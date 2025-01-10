import shared
import SwiftUI

protocol LoginViewModelProtocol: DynamicProperty {
    func onLoginClick()
}

struct LoginViewModel {
    @StateObject @KotlinStateFlow private var viewState: LoginViewState
    private let actions: LoginScreenActions

    init(_ screen: LoginScreen) {
        _viewState = .init(screen.viewState)
        actions = screen.actions
    }
}

extension LoginViewModel: LoginViewModelProtocol {
    func onLoginClick() {
        actions.onLoginClick()
    }
}
