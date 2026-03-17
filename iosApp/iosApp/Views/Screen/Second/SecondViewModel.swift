import KMP
import SwiftUI

protocol SecondViewModelProtocol: DynamicProperty {
    var createdAt: String { get }
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

    var createdAt: String {
        viewState.createdAtText.localized()
    }

    func onPickFruit() {
        actions.onPickFruit()
    }

    func onPickVeggie() {
        actions.onPickVeggie()
    }
}
