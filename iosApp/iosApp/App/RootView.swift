import shared
import SwiftUI

struct RootView: View {

    @State private var componentHolder = ComponentHolder {
        RootNavHostFactory().create(componentContext: $0)
    }

    @Environment(\.scenePhase)
    var scenePhase: ScenePhase

    var body: some View {
        RootNavigationView(componentHolder.component)
            .onChange(of: scenePhase) { newPhase in
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
