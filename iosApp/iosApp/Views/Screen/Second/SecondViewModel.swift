import shared
import SwiftUI

protocol SecondViewModelProtocol: DynamicProperty {
    var text: String { get }

    func onNext()
    func onBack()
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
    var text: String {
        viewState.text.localized()
    }

    func onNext() {
        actions.onNext()
    }

    func onBack() {
        actions.onBack()
    }
}
