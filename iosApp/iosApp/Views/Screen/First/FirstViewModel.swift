import KMP
import SwiftUI

protocol FirstViewModelProtocol: DynamicProperty {

    var events: SkieSwiftFlow<FirstUiEvent> { get }
    var isAlertVisible: Binding<Bool> { get }
    var alertText: String { get }

    func onNext()
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

    var isAlertVisible: Binding<Bool> {
        Binding(
            get: { alertVisible },
            set: { alertVisible = $0 }
        )
    }

    func onNext() {
    }

    func hideToast() {
        alertVisible = false
    }
}
