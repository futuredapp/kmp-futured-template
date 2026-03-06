import KMP
import Observation

protocol SecondComponentModelProtocol: AnyObject {
    func onPickFruit()
    func onPickVeggie()
}

@Observable
final class SecondComponentModel: SecondComponentModelProtocol {
    private var viewState: SecondViewState

    @ObservationIgnored private let actions: SecondScreenActions
    @ObservationIgnored private var stateTask: Task<Void, Never>?

    init(_ screen: SecondScreen) {
        viewState = screen.viewState.value
        actions = screen.actions
        stateTask = Task { [weak self] in
            for await state in screen.viewState {
                self?.viewState = state
            }
        }
    }

    deinit {
        stateTask?.cancel()
    }

    func onPickFruit() {
        actions.onPickFruit()
    }

    func onPickVeggie() {
        actions.onPickVeggie()
    }
}

#if DEBUG
@Observable
final class SecondComponentModelMock: SecondComponentModelProtocol {
    func onPickFruit() {
        print("Pick fruit tapped")
    }

    func onPickVeggie() {
        print("Pick veggie tapped")
    }
}
#endif
