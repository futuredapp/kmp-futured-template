import shared
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
        KmpApplication().initializeSharedApplication(platformBindings: PlatformBindingsImpl())
    }
}
// swiftlint:enable discouraged_optional_collection
