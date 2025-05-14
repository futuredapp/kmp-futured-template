import KMP
import SwiftUI

protocol FormViewModelProtocol: DynamicProperty {
    var screen: FormScreen { get }
}

struct FormViewModel {
    let screen: FormScreen

    init(_ screen: FormScreen) {
        self.screen = screen
    }
}

extension FormViewModel: FormViewModelProtocol {
}
