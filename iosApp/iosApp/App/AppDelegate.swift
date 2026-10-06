import KMP
import SwiftUI

final class AppDelegate: NSObject, UIApplicationDelegate {

    func application(
        _ application: UIApplication, // swiftlint:disable:next discouraged_optional_collection
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
