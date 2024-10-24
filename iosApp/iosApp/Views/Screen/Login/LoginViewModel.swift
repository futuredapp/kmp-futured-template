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
    private let crashlyticsService: TemplateService

    init(_ screen: LoginScreen, templateService: TemplateService) {
        self._viewState = .init(screen.viewState)
        self.actions = screen.actions
        self.suspendActions = screen.suspendActions
        self.templateService = templateService
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
