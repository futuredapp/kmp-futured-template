import KMP
import Observation

protocol SignedInNavigationComponentModelProtocol {
    var selectedTab: NavigationTab { get set }
    var homeTab: SignedInChildHome? { get }
    var profileTab: SignedInChildProfile? { get }
}

@Observable
final class SignedInNavigationComponentModel: SignedInNavigationComponentModelProtocol {

    // MARK: Public computed properties

    var selectedTab: NavigationTab {
        get { viewState.selectedTab }
        set { actions.onTabSelected(tab: newValue) }
    }

    // MARK: Private stored properties

    private var viewState: SignedInNavHostViewState
    private(set) var homeTab: SignedInChildHome?
    private(set) var profileTab: SignedInChildProfile?

    // MARK: Private @ObservationIgnored properties

    @ObservationIgnored private let actions: SignedInNavHostActions
    @ObservationIgnored private var homeTabTask: Task<Void, Never>?
    @ObservationIgnored private var profileTabTask: Task<Void, Never>?
    @ObservationIgnored private var stateTask: Task<Void, Never>?

    // MARK: Init / Deinit

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
}
