import KMP
import SwiftUI

protocol ThirdViewModelProtocol: DynamicProperty {
    var text: String { get }
}

struct ThirdViewModel {
    @StateObject @KotlinStateFlow private var viewState: ThirdViewState
    private let actions: ThirdScreenActions

    init(_ screen: ThirdScreen) {
        _viewState = .init(screen.viewState)
        actions = screen.actions
    }
}

extension ThirdViewModel: ThirdViewModelProtocol {
    var text: String {
        viewState.text.localized()
    }
}
