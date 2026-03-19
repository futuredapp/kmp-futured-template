import KMP
import Observation

/// Observes a nullable Kotlin `StateFlow` and exposes its value to SwiftUI via `@Observable`.
///
/// Optional variant of ``StateFlowObserver`` for flows that may emit `nil`.
/// See ``StateFlowObserver`` for the KMP–FuturedKit equivalence rationale.
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
