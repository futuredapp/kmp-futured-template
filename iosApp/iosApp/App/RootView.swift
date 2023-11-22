import shared
import SwiftUI

struct RootView: View {
    @State private var componentHolder = ComponentHolder {
        RootNavigationFactory().create(componentContext: $0)
    }

    var body: some View {
        RootNavigationView(componentHolder.component)
            .onAppear {
                LifecycleRegistryExtKt.resume(self.componentHolder.lifecycle)
            }
            .onDisappear {
                LifecycleRegistryExtKt.stop(self.componentHolder.lifecycle)
            }
    }
}
