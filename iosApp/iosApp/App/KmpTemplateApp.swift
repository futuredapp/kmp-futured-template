import SwiftUI

@main
struct KmpTemplateApp: App {
    @UIApplicationDelegateAdaptor private var appDelegate: AppDelegate
    @StateObject private var coordinator: AppCoordinator

    @Environment(\.scenePhase)
    var scenePhase: ScenePhase

    init() {
        let coordinator = AppCoordinator()
        self._coordinator = StateObject(wrappedValue: coordinator)
        self.appDelegate.delegate = coordinator
    }

    var body: some Scene {
        WindowGroup {
            coordinator.rootView
                .onChange(of: scenePhase) { newPhase in
                    coordinator.scenePhaseChanged(newPhase: newPhase)

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
                .onOpenURL { url in
                    coordinator.openDeeplink(url: url)
                }
        }
    }
}
