import KMP
import Observation

/// KMP deviation: Conforms to `AnyObject` instead of FuturedKit's `ComponentModel` protocol
/// because navigation and event handling are managed by KMP Decompose, not a Swift Coordinator.
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
