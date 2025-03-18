import KMP
import SwiftUI

@propertyWrapper
final class KotlinOptionalStateFlow<T: AnyObject>: ObservableObject {

    private let stateFlow: SkieSwiftOptionalStateFlow<T>

    @Published var wrappedValue: T?

    private var publisher: Task<(), Never>?

    init(_ value: SkieSwiftOptionalStateFlow<T>) {
        self.stateFlow = value
        self.wrappedValue = value.value

        self.publisher = Task { @MainActor [weak self] in
            if let stateFlow = self?.stateFlow {
                for await item in stateFlow {
                    self?.wrappedValue = item
                }
            }
        }
    }

    deinit {
        if let publisher {
            publisher.cancel()
        }
    }
}

extension ObservedObject {
    init<F>(_ stateFlow: SkieSwiftOptionalStateFlow<F>) where ObjectType == KotlinOptionalStateFlow<F> {
        self.init(wrappedValue: KotlinOptionalStateFlow(stateFlow))
    }
}

extension StateObject {
    init<F>(_ stateFlow: SkieSwiftOptionalStateFlow<F>) where ObjectType == KotlinOptionalStateFlow<F> {
        self.init(wrappedValue: KotlinOptionalStateFlow(stateFlow))
    }
}
