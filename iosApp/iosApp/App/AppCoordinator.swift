import SwiftUI

final class AppCoordinator: ObservableObject {
    private var container: Container
    private var componentHolder: ComponentHolder = RootNavigationFactory().create(componentContext: $0)

    init(container: Container = Container()) {
        self.container = container
    }

    var rootView: some View {
        RootNavigationView(componentHolder.component)
    }

    func openDeeplink(url: URL) {
        componentHolder.component.actions.openDeepLink(url: url.absoluteString)
    }

    func scenePhaseChanged(newPhase: ScenePhase) {
        // Handle scene phase change
    }
}

extension AppCoordinator: AppDelegateProtocol {
    // swiftlint:disable:next discouraged_optional_collection
    func applicationDidFinishLaunching(with launchOptions: [UIApplication.LaunchOptionsKey: Any]?) {
        KmpApplication().initializeSharedApplication(platformBindings: container)
    }
}
