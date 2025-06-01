import AVFoundation
import KMP
import SwiftUI

protocol WelcomeViewModelProtocol: DynamicProperty {

    var events: SkieSwiftFlow<WelcomeUIEvent> { get }
    var isLoading: Bool { get }
    var state: StateViewState { get }

    func askForCameraPermission()
}

struct WelcomeViewModel {
    @ObservedObject @KotlinStateFlow private var viewState: WelcomeViewState
    let events: SkieSwiftFlow<WelcomeUIEvent>

    @State var isLoading: Bool = false

    private let actions: WelcomeScreenActions

    init(_ screen: WelcomeScreen) {
        self._viewState = .init(screen.viewState)
        events = screen.events
        self.actions = screen.actions
    }
}

extension WelcomeViewModel: WelcomeViewModelProtocol {
    var state: StateViewState {
        StateViewState(dialog: viewState.uiDialog, error: nil, retryAction: nil)
    }

    func askForCameraPermission() {
        Task { @MainActor in
            isLoading = true
            if await AVCaptureDevice.requestAccess(for: .video) {
                self.actions.onCameraPermissionGranted()
            } else {
                self.actions.onCameraPermissionDenied()
            }
            isLoading = false
        }
    }
}
