import shared

/**
 This class is responsible for managing a root Decompose component at the application root.
 */
final class ComponentHolder<T> {
    let lifecycle: LifecycleRegistry
    let component: T

    init(factory: (ComponentContext) -> T) {
        let lifecycle = LifecycleRegistryKt.LifecycleRegistry()
        let component = factory(DefaultComponentContext(lifecycle: lifecycle))
        self.lifecycle = lifecycle
        self.component = component

        LifecycleRegistryExtKt.create(lifecycle)
    }

    deinit {
        LifecycleRegistryExtKt.create(lifecycle)
    }
}
