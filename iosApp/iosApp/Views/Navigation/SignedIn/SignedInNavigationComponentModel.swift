import KMP
import Observation

@Observable
final class SignedInNavigationComponentModel {
    private(set) var homeTab: SignedInChildHome?
    private(set) var profileTab: SignedInChildProfile?

    var selectedTab: NavigationTab {
        viewState.selectedTab
    }

    private var viewState: SignedInNavHostViewState

    @ObservationIgnored private let actions: SignedInNavHostActions
    @ObservationIgnored private var homeTabTask: Task<Void, Never>?
    @ObservationIgnored private var profileTabTask: Task<Void, Never>?
    @ObservationIgnored private var stateTask: Task<Void, Never>?

    init(_ component: SignedInNavHost) {
        homeTab = component.homeTab.value
        profileTab = component.profileTab.value
        viewState = component.viewState.value
        actions = component.actions

        homeTabTask = Task { [weak self] in
            for await state in component.homeTab {
                self?.homeTab = state
            }
        }
        profileTabTask = Task { [weak self] in
            for await state in component.profileTab {
                self?.profileTab = state
            }
        }
        stateTask = Task { [weak self] in
            for await state in component.viewState {
                self?.viewState = state
            }
        }
    }

    deinit {
        homeTabTask?.cancel()
        profileTabTask?.cancel()
        stateTask?.cancel()
    }

    func onTabSelected(_ tab: NavigationTab) {
        actions.onTabSelected(tab: tab)
    }
}
