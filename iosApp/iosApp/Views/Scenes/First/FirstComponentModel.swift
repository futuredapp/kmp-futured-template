import KMP
import Observation

/// KMP deviation: Conforms to `AnyObject` instead of FuturedKit's `ComponentModel` protocol
/// because navigation and event handling are managed by KMP Decompose, not a Swift Coordinator.
protocol FirstComponentModelProtocol: AnyObject {
    var counter: String { get }
    var createdAt: String { get }
    var randomPerson: String? { get }
    var alert: AlertModel? { get set }

    func onNext()
}

@Observable
final class FirstComponentModel: FirstComponentModelProtocol {
    var alert: AlertModel?

    var counter: String {
        viewState.counter.localized()
    }
    var createdAt: String {
        viewState.createdAt.localized()
    }
    var randomPerson: String? {
        viewState.randomPerson?.localized()
    }

    private var viewState: FirstViewState

    @ObservationIgnored private let actions: FirstScreenActions
    @ObservationIgnored private var stateTask: Task<Void, Never>?
    @ObservationIgnored private var eventsTask: Task<Void, Never>?

    init(_ screen: FirstScreen) {
        viewState = screen.viewState.value
        actions = screen.actions

        stateTask = Task { [weak self] in
            for await state in screen.viewState {
                self?.viewState = state
            }
        }

        eventsTask = Task { [weak self] in
            for await event in screen.events {
                self?.handleEvent(event)
            }
        }
    }

    deinit {
        stateTask?.cancel()
        eventsTask?.cancel()
    }

    func onNext() {
        actions.onNext()
    }

    // MARK: Utilities

    private func handleEvent(_ event: FirstUiEvent) {
        switch onEnum(of: event) {
        case let .showToast(toast):
            alert = AlertModel(message: toast.text.localized())
        }
    }
}

#if DEBUG
@Observable
final class FirstComponentModelMock: FirstComponentModelProtocol {
    var counter = "42"
    var createdAt = "2024-01-01"
    var randomPerson: String? = "Obi-Wan Kenobi"
    var alert: AlertModel?

    func onNext() {
        print("Next tapped")
    }
}
#endif
