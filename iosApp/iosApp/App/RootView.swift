import KMP
import SwiftUI

struct RootView: View {

    @Environment(\.scenePhase)
    var scenePhase: ScenePhase

    @State private var componentHolder = ComponentHolder {
        RootNavHostFactory().create(componentContext: $0)
    }

    var body: some View {
        RootNavigationView(componentHolder.component)
            .onChange(of: scenePhase) { _, newPhase in
                switch newPhase {
                case .background:
                    LifecycleRegistryExtKt.stop(componentHolder.lifecycle)
                case .inactive:
                    LifecycleRegistryExtKt.pause(componentHolder.lifecycle)
                case .active:
                    LifecycleRegistryExtKt.resume(componentHolder.lifecycle)
                @unknown default:
                    break
                }
            }
    }
}
