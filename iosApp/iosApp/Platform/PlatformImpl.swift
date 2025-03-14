import KMP
import UIKit.UIDevice

final class PlatformImpl: Platform {

    var appName: String {
        Bundle.main.infoDictionary?["CFBundleName"] as? String ?? "Unknown"
    }

    var appVersionName: String {
        Bundle.main.infoDictionary?["CFBundleShortVersionString"] as? String ?? "Unnown"
    }

    var appBuildNumber: String {
        Bundle.main.infoDictionary?["CFBundleVersion"] as? String ?? "Unknown"
    }

    var applicationId: String {
        Bundle.main.bundleIdentifier ?? "Unknown"
    }

    var deviceModel: String {
        var sysinfo = utsname()
        uname(&sysinfo)
        return String(bytes: Data(bytes: &sysinfo.machine, count: Int(_SYS_NAMELEN)), encoding: .ascii)?.trimmingCharacters(in: .controlCharacters) ?? "Unknown"
    }

    var osNameWithVersion: String {
        let currentDevice = UIDevice.current
        return "\(currentDevice.systemName) \(currentDevice.systemVersion)"
    }
}
