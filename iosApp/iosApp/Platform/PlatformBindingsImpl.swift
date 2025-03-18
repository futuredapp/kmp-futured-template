import Foundation
import KMP

final class PlatformBindingsImpl: PlatformBindings {

    func platform() -> any Platform {
        PlatformImpl()
    }

    func firebaseCrashlytics() -> PlatformFirebaseCrashlytics {
        PlatformFirebaseCrashlyticsImpl()
    }
}
