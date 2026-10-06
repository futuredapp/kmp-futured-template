import KMP
import Observation

/// Observes a Kotlin `StateFlow` and exposes its value to SwiftUI via `@Observable`.
///
/// This is the KMP equivalent of FuturedKit's `DataCache` observation pattern.
/// Instead of `DataCache<Model>`, state lives in Kotlin's `StateFlow` and this
/// observer bridges it reactively into Swift's Observation framework.
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
