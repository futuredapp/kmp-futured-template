import KMP
import SwiftUI

protocol LoginViewModelProtocol: DynamicProperty {
    func onLoginClick()
}

struct LoginViewModel {
    @StateObject @KotlinStateFlow private var viewState: WelcomeViewState
    private let actions: WelcomeScreenActions

    init(_ screen: WelcomeScreen) {
        _viewState = .init(screen.viewState)
        actions = screen.actions
    }
}

extension LoginViewModel: LoginViewModelProtocol {
    func onLoginClick() {
    }
}
