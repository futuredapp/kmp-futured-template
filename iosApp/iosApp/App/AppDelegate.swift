import KMP
import SwiftUI

// swiftlint:disable discouraged_optional_collection
final class AppDelegate: NSObject, UIApplicationDelegate {


    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]? = nil
    ) -> Bool {
        initializeSharedApplication()
        return true
    }

    private func initializeSharedApplication() {
        let isDebugBuild: Bool

        #if DEBUG
        isDebugBuild = true
        #else
        isDebugBuild = false
        #endif

        KmpApplication().initializeSharedApplication(platformBindings: PlatformBindingsImpl(), isDebugBuild: isDebugBuild)
    }
}
// swiftlint:enable discouraged_optional_collection
