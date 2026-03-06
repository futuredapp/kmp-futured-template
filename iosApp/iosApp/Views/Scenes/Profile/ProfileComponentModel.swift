import KMP
import Observation

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
    func onLogoutClick() { print("Logout tapped") }
    func onThirdClick() { print("Third tapped") }
}
#endif
