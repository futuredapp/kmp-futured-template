import KMP
import Observation

@Observable
final class OptionalStateFlowObserver<T: AnyObject> {
    private(set) var value: T?

    @ObservationIgnored private var task: Task<Void, Never>?

    init(_ stateFlow: SkieSwiftOptionalStateFlow<T>) {
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
