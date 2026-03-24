import KMP
import Observation

protocol RootNavigationComponentModelProtocol {
    var slotChild: RootChild? { get }

    func onDeepLink(_ url: String)
}

@Observable
final class RootNavigationComponentModel: RootNavigationComponentModelProtocol {

    // MARK: Public computed properties

    var slotChild: RootChild? { _slot.child?.instance }

    // MARK: Private stored properties

    private var _slot: ChildSlot<RootConfig, RootChild>

    // MARK: Private @ObservationIgnored properties

    @ObservationIgnored private let actions: RootNavHostActions
    @ObservationIgnored private var stateTask: Task<Void, Never>?

    // MARK: Init / Deinit

    init(_ component: RootNavHost) {
        _slot = component.slot.value
        actions = component.actions

        stateTask = Task { [weak self] in
            for await state in component.slot {
                self?._slot = state
            }
        }
    }

    deinit {
        stateTask?.cancel()
    }

    // MARK: Public functions

    func onDeepLink(_ url: String) {
        actions.onDeepLink(uri: url)
    }
}
