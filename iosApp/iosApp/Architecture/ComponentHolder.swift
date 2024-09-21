import shared

/**
 This class is responsible for managing a root Decompose component at the application root.
 */
final class ComponentHolder<T> {
    let lifecycle: LifecycleRegistry
    let component: T

    init(factory: (AppComponentContext) -> T) {
        let lifecycle = LifecycleRegistryKt.LifecycleRegistry()
        let genericComponentContext = DefaultComponentContext(lifecycle: lifecycle)
        let appComponentContext = DefaultAppComponentContext(componentContext: genericComponentContext)
        let component = factory(appComponentContext)
        self.lifecycle = lifecycle
        self.component = component

        LifecycleRegistryExtKt.create(lifecycle)
    }

    deinit {
        // Destroy the root component before it is deallocated
        LifecycleRegistryExtKt.destroy(lifecycle)
    }
}
