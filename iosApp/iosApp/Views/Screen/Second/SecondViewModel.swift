import KMP
import SwiftUI

protocol SecondViewModelProtocol: DynamicProperty {
    func onPickFruit()
    func onPickVeggie()
}

struct SecondViewModel {
    @StateObject @KotlinStateFlow private var viewState: SecondViewState
    private let actions: SecondScreenActions

    init(_ screen: SecondScreen) {
        _viewState = .init(screen.viewState)
        actions = screen.actions
    }
}

extension SecondViewModel: SecondViewModelProtocol {

    func onPickFruit() {
        actions.onPickFruit()
    }

    func onPickVeggie() {
        actions.onPickVeggie()
    }
}
