import KMP
import SwiftUI

protocol ProfileViewModelProtocol: DynamicProperty {
    func onLogoutClick()
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
}
