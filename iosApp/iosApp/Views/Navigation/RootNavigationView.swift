import shared
import SwiftUI

struct RootNavigationView: View {
    @StateObject @KotlinStateFlow private var slot: ChildSlot<RootDestination, RootEntry>

    private let container: Container

    init(_ component: RootNavigation, container: Container) {
        self._slot = .init(component.slot)
        self.container = container
    }

    var body: some View {
        ZStack {
            if let navigationEntry = slot.child?.instance {
                switch onEnum(of: navigationEntry) {
                case .login(let entry):
                    LoginView(LoginViewModel(entry.instance, templateService: container.templateService))
                case .signedIn(let entry):
                    SignedInNavigationView(entry.instance, container: container)
                }
            }
        }
    }
}
