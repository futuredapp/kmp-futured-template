import Foundation
import shared

final class PlatformBindingsImpl: PlatformBindings {

    func platform() -> any Platform {
        PlatformImpl()
    }

    func firebaseCrashlytics() -> PlatformFirebaseCrashlytics {
        PlatformFirebaseCrashlyticsImpl()
    }
}
