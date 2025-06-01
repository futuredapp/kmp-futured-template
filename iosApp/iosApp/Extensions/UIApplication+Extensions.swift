import UIKit

extension UIApplication {
    func openSystemSettings() {
        guard let settingsURL = URL(string: UIApplication.openSettingsURLString) else {
            return
        }
        open(settingsURL)
    }
}
