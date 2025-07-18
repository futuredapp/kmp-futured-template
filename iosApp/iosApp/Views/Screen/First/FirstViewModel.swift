import KMP
import SwiftUI

protocol FirstViewModelProtocol: DynamicProperty {
    var counter: String { get }
    var createdAt: String { get }
    var randomPerson: String? { get }
    var events: SkieSwiftFlow<FirstUiEvent> { get }
    var isAlertVisible: Binding<Bool> { get }
    var alertText: String { get }

    func onNext()
    func showToast(event: FirstUiEvent.ShowToast)
    func hideToast()
}

struct FirstViewModel {
    @StateObject @KotlinStateFlow private var viewState: FirstViewState
    private let actions: FirstScreenActions
    let events: SkieSwiftFlow<FirstUiEvent>

    @State private var alertVisible: Bool = false
    @State private(set) var alertText: String = ""

    init(_ screen: FirstScreen) {
        _viewState = .init(screen.viewState)
        actions = screen.actions
        events = screen.events
    }
}

extension FirstViewModel: FirstViewModelProtocol {
    var counter: String {
        viewState.counter.localized()
    }

    var createdAt: String {
        viewState.createdAt.localized()
    }

    var randomPerson: String? {
        viewState.randomPerson?.localized()
    }

    var isAlertVisible: Binding<Bool> {
        Binding(
            get: { alertVisible },
            set: { alertVisible = $0 }
        )
    }

    func onNext() {
        actions.onNext()
    }

    func showToast(event: FirstUiEvent.ShowToast) {
        alertText = event.text.localized()
        alertVisible = true
    }

    func hideToast() {
        alertVisible = false
    }
}
