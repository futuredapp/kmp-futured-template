import AVFoundation
import FuturedKit
import SwiftUI

/// A view that displays a camera for picking photo.
///
/// ## Overview
///
/// Camera image picker uses ``WrappedUIImagePicker`` and automatically shows the alert if
/// camera permission status is unauthorized.
/// After selection is the view automatically dismissed.
public struct SelfieView: View {
    // swiftlint:disable:next attributes
    @Environment(\.presentationMode) private var presentationMode
    @Binding private var selection: UIImage?
    @State private var permissionAlertModel: AlertModel?

    private let cameraPermissionAlertConfiguration: CameraPermissionAlertConfiguration

    /// A configuration for unauthorized permission alert.
    public struct CameraPermissionAlertConfiguration {
        let title: String
        let message: String
        let dismissButtonTitle: String
        let settingsButtonTitle: String

        /// Creates an permission alert configuration.
        /// - Parameters:
        ///   - title: The title of the alert.
        ///   - message: The message to display in the body of the alert.
        ///   - dismissButtonTitle: The title of the cancel button.
        ///   - settingsButtonTitle: The title of the button which action goes to settings.
        public init(
            title: String,
            message: String,
            dismissButtonTitle: String,
            settingsButtonTitle: String
        ) {
            self.title = title
            self.message = message
            self.dismissButtonTitle = dismissButtonTitle
            self.settingsButtonTitle = settingsButtonTitle
        }
    }

    /// Creates a picker that selects an item and configures alert in camera permission status is unauthorized.
    /// - Parameters:
    ///   - selection: The selected photo.
    ///   - cameraPermissionAlertConfiguration: The alert configuration for unauthorized permission status.
    public init (
        selection: Binding<UIImage?>,
        cameraPermissionAlertConfiguration: CameraPermissionAlertConfiguration
    ) {
        self._selection = selection
        self.cameraPermissionAlertConfiguration = cameraPermissionAlertConfiguration
    }

    public var body: some View {

        WrappedUIImagePicker(didFinishPickingMediaWithInfo: handlePickerResult) { viewController in
            viewController.sourceType = .camera
            viewController.cameraDevice = .front
        }
        .background(Color(.appBlack).edgesIgnoringSafeArea(.bottom))
        .onAppear(perform: checkCameraAuthorizationStatus)
        .alert(model: $permissionAlertModel)
    }

    private var cameraPermissionAlert: AlertModel {
        AlertModel(
            title: cameraPermissionAlertConfiguration.title,
            message: cameraPermissionAlertConfiguration.message,
            action: .custom(
                primary: .cancel(
                    Text(cameraPermissionAlertConfiguration.dismissButtonTitle),
                    action: dismiss
                ),
                secondary: .default(
                    Text(cameraPermissionAlertConfiguration.settingsButtonTitle)
                ) {
                        goToSettings()
                        dismiss()
                }
            )
        )
    }

    private func dismiss() {
        presentationMode.wrappedValue.dismiss()
    }

    private func goToSettings() {
        if let url = URL(string: UIApplication.openSettingsURLString) {
            UIApplication.shared.open(url)
        }
    }

    private func checkCameraAuthorizationStatus() {
        switch AVCaptureDevice.authorizationStatus(for: .video) {
        case .authorized:
            break
        case .notDetermined:
            AVCaptureDevice.requestAccess(for: .video) { granted in
                DispatchQueue.main.async {
                    if !granted {
                        permissionAlertModel = cameraPermissionAlert
                    }
                }
            }
        case .denied, .restricted:
            DispatchQueue.main.async {
                self.permissionAlertModel = cameraPermissionAlert
            }
        @unknown default:
            assertionFailure("Unknown state")
        }
    }

    private func handlePickerResult(with info: [UIImagePickerController.InfoKey: Any]) {
        if let image = info[UIImagePickerController.InfoKey.originalImage] as? UIImage {
            selection = image
            dismiss()
        }
    }
}
