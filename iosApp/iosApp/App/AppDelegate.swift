import shared
import SwiftUI

protocol AppDelegateProtocol: AnyObject {
    func applicationDidFinishLaunching(with launchOptions: [UIApplication.LaunchOptionsKey: Any]?)
}

// swiftlint:disable discouraged_optional_collection
final class AppDelegate: NSObject, UIApplicationDelegate {
    weak var delegate: AppDelegateProtocol?

    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]? = nil
    ) -> Bool {
        delegate?.applicationDidFinishLaunching(with: launchOptions)
        return true
    }
}
// swiftlint:enable discouraged_optional_collection
