import KMP
import Observation

@Observable
final class HomeTabNavigationComponentModel {
    private(set) var sheet: ChildSlot<HomeSheetConfig, HomeSheetChild>

    @ObservationIgnored let stack: SkieSwiftStateFlow<ChildStack<HomeConfig, HomeChild>>
    @ObservationIgnored let actions: HomeNavHostActions
    @ObservationIgnored private var stateTask: Task<Void, Never>?

    init(_ component: HomeNavHost) {
        sheet = component.sheet.value
        stack = component.stack
        actions = component.actions

        stateTask = Task { [weak self] in
            for await state in component.sheet {
                self?.sheet = state
            }
        }
    }

    deinit {
        stateTask?.cancel()
    }

    func onSheetDismissed() {
        actions.onSheetDismissed()
    }
}
