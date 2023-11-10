import shared
import SwiftUI

@propertyWrapper
final class KotlinStateFlow<T: AnyObject>: ObservableObject {
    
    private let stateFlow: SkieSwiftStateFlow<T>
    
    @Published
    var wrappedValue: T
    
    private var publisher: Task<(), Never>?
    
    init(_ value: SkieSwiftStateFlow<T>) {
        self.stateFlow = value
        self.wrappedValue = value.value
        
        self.publisher = Task { @MainActor in
            for await item in stateFlow {
                self.wrappedValue = item
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
    init<F>(_ stateFlow: SkieSwiftStateFlow<F>) where ObjectType == KotlinStateFlow<F> {
        self.init(wrappedValue: KotlinStateFlow(stateFlow))
    }
}
