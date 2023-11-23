import Foundation
import shared

final class PlatformBindingsImpl: PlatformBindings {

    func firebaseCrashlytics() -> PlatformFirebaseCrashlytics {
        PlatformFirebaseCrashlyticsImpl()
    }
}
