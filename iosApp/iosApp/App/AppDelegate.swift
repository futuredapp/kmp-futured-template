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
        KmpApplication().initializeSharedApplication(platformBindings: PlatformBindingsImpl())
    }
}
