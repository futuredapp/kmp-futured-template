import KMP
import Observation

protocol ProfileTabNavigationComponentModelProtocol {
    var stack: SkieSwiftStateFlow<ChildStack<ProfileConfig, ProfileChild>> { get }

    func navigate(_ path: [ChildCreated<ProfileConfig, ProfileChild>])
}

@Observable
final class ProfileTabNavigationComponentModel: ProfileTabNavigationComponentModelProtocol {

    // MARK: Private @ObservationIgnored properties

    @ObservationIgnored let stack: SkieSwiftStateFlow<ChildStack<ProfileConfig, ProfileChild>>
    @ObservationIgnored private let actions: ProfileNavHostActions

    // MARK: Init / Deinit

    init(_ component: ProfileNavHost) {
        stack = component.stack
        actions = component.actions
    }

    // MARK: Public functions

    func navigate(_ path: [ChildCreated<ProfileConfig, ProfileChild>]) {
        actions.navigate(newStack: path)
    }
}
