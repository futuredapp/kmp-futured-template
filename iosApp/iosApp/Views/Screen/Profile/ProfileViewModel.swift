import shared
import SwiftUI

protocol ProfileViewModelProtocol: DynamicProperty {
    func onLogoutClick()
    func onThirdClick()
}

struct ProfileViewModel {
    private let actions: ProfileScreenActions

    init(_ screen: ProfileScreen) {
        actions = screen.actions
    }
}

extension ProfileViewModel: ProfileViewModelProtocol {
    func onLogoutClick() {
        actions.onLogout()
    }
    func onThirdClick() {
        actions.onThird()
    }
}
