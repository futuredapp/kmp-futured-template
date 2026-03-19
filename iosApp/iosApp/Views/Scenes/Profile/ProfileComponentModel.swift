import KMP
import Observation

/// KMP deviation: Conforms to `AnyObject` instead of FuturedKit's `ComponentModel` protocol
/// because navigation and event handling are managed by KMP Decompose, not a Swift Coordinator.
protocol ProfileComponentModelProtocol: AnyObject {
    func onLogoutClick()
    func onThirdClick()
}

@Observable
final class ProfileComponentModel: ProfileComponentModelProtocol {
    @ObservationIgnored private let actions: ProfileScreenActions

    init(_ screen: ProfileScreen) {
        actions = screen.actions
    }

    func onLogoutClick() {
        actions.onLogout()
    }

    func onThirdClick() {
        actions.onThird()
    }
}

#if DEBUG
@Observable
final class ProfileComponentModelMock: ProfileComponentModelProtocol {
    func onLogoutClick() {
        print("Logout tapped")
    }

    func onThirdClick() {
        print("Third tapped")
    }
}
#endif
