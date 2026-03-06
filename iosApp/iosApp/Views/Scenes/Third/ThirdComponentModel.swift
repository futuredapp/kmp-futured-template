import KMP
import Observation

protocol ThirdComponentModelProtocol: AnyObject {
    var text: String { get }
}

@Observable
final class ThirdComponentModel: ThirdComponentModelProtocol {
    var text: String {
        viewState.text.localized()
    }

    private var viewState: ThirdViewState

    @ObservationIgnored private var stateTask: Task<Void, Never>?

    init(_ screen: ThirdScreen) {
        viewState = screen.viewState.value
        stateTask = Task { [weak self] in
            for await state in screen.viewState {
                self?.viewState = state
            }
        }
    }

    deinit {
        stateTask?.cancel()
    }
}

#if DEBUG
@Observable
final class ThirdComponentModelMock: ThirdComponentModelProtocol {
    var text = "You picked: Golden Delicious"
}
#endif
