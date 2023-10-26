import shared
import SwiftUI

/**
 This is a wrapper over [Value] object from shared KMM framework, which allows
 SwiftUI views observe this value as @ObservedObject property delegate.
 */
@propertyWrapper
final class KotlinState<T: AnyObject>: ObservableObject {
    private let observableValue: Value<T>

    @Published var wrappedValue: T

    private var observer: ((T) -> Void)?

    init(_ value: Value<T>) {
        self.observableValue = value
        self.wrappedValue = observableValue.value
        self.observer = { [weak self] value in self?.wrappedValue = value }

        if let observer {
            observableValue.subscribe(observer: observer)
        }
    }

    deinit {
        if let observer {
            observableValue.unsubscribe(observer: observer)
        }
    }
}

extension ObservedObject {
    init<F>(_ decomposeValue: Value<F>) where ObjectType == KotlinState<F> {
        self.init(wrappedValue: KotlinState(decomposeValue))
    }
}
