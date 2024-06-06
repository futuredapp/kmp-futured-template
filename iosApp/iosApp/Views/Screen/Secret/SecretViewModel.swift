import shared
import SwiftUI

protocol SecretViewModelProtocol: DynamicProperty {
    var text: String { get }

    func goBack()
}

struct SecretViewModel {
    private let screen: SecretScreen

    init(_ screen: SecretScreen) {
        self.screen = screen
    }
}

extension SecretViewModel: SecretViewModelProtocol {
    var text: String {
        screen.text.localized()
    }

    func goBack() {
        screen.goBack()
    }
}
