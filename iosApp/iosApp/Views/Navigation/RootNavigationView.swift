import AVFoundation
import KMP
import SwiftUI

struct RootNavigationView: View {

    @StateObject @KotlinStateFlow private var slot: ChildSlot<RootConfig, RootChild>
    private let openDeepLink: (String) -> Void
    private let setInitialStack: (Bool) -> Void

    init(_ component: RootNavHost) {
        self._slot = .init(component.slot)
        self.openDeepLink = component.actions.onDeepLink
        self.setInitialStack = component.actions.updateCameraPermission
    }

    var body: some View {
        ZStack {
            if let navigationEntry = slot.child?.instance {
                switch onEnum(of: navigationEntry) {
                case .intro(let entry):
                    WelcomeView(WelcomeViewModel(entry.screen)).id(entry.iosViewId)
                case .home(let entry):
                    HomeTabNavigationView(entry.navHost).id(entry.iosViewId)
                }
            }
        }
        .onOpenURL { url in
            openDeepLink(url.absoluteString)
        }
        .task {
            setInitialStack(AVCaptureDevice.authorizationStatus(for: .video) == .authorized)
        }
    }
}
