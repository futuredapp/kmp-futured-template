import shared
import SwiftUI

final class AppDelegate: NSObject, UIApplicationDelegate {
    
    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil
    ) -> Bool {
        initializeSharedApplication()
        return true
    }
    
    private func initializeSharedApplication() {
        KmpApplication()
            .initializeSharedApplication(
                nativePlatformModule: NativePlatformModuleImpl()
            )
    }
}
