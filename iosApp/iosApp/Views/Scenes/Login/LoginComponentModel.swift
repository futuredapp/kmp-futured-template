import KMP
import Observation

protocol LoginComponentModelProtocol: AnyObject {
    func onLoginClick()
}

@Observable
final class LoginComponentModel: LoginComponentModelProtocol {
    @ObservationIgnored private let actions: LoginScreenActions

    init(_ screen: LoginScreen) {
        actions = screen.actions
    }

    func onLoginClick() {
        actions.onLoginClick()
    }
}

#if DEBUG
@Observable
final class LoginComponentModelMock: LoginComponentModelProtocol {
    func onLoginClick() {
        print("Login tapped")
    }
}
#endif
