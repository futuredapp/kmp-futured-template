import KMP
import Observation

@Observable
final class RootNavigationComponentModel {
    private(set) var slot: ChildSlot<RootConfig, RootChild>

    @ObservationIgnored private let openDeepLink: (String) -> Void
    @ObservationIgnored private var stateTask: Task<Void, Never>?

    init(_ component: RootNavHost) {
        slot = component.slot.value
        openDeepLink = component.actions.onDeepLink

        stateTask = Task { [weak self] in
            for await state in component.slot {
                self?.slot = state
            }
        }
    }

    deinit {
        stateTask?.cancel()
    }

    func onDeepLink(_ url: String) {
        openDeepLink(url)
    }
}
