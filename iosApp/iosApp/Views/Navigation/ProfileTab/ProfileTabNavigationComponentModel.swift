import KMP
import Observation

@Observable
final class ProfileTabNavigationComponentModel {
    @ObservationIgnored let stack: SkieSwiftStateFlow<ChildStack<ProfileConfig, ProfileChild>>
    @ObservationIgnored let actions: ProfileNavHostActions

    init(_ component: ProfileNavHost) {
        stack = component.stack
        actions = component.actions
    }
}
