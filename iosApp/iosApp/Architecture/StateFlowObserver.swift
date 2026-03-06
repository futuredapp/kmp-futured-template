import KMP
import Observation

@Observable
final class StateFlowObserver<T: AnyObject> {
    private(set) var value: T
    @ObservationIgnored private var task: Task<Void, Never>?

    init(_ stateFlow: SkieSwiftStateFlow<T>) {
        value = stateFlow.value
        task = Task { [weak self] in
            for await state in stateFlow {
                self?.value = state
            }
        }
    }

    deinit {
        task?.cancel()
    }
}
