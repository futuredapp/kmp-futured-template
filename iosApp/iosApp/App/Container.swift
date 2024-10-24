
import Foundation

// Container used to hold shared iOS services through the app
// Use dependency injection to inject services to scenes or other services
final class Container {
    let crashlyticsService: CrashlyticsService

    init() {
        self.crashlyticsService = ProductionCrashlyticsService()
    }

    func resetContainer() {
        crashlyticsService.reset()
    }
}

extension Container: PlatformBindings {
    func platform() -> any Platform {
        PlatformImpl()
    }

    func firebaseCrashlytics() -> PlatformFirebaseCrashlytics {
        crashlyticsService
    }
}
