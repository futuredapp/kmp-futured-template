import KMP
import Observation

protocol HomeTabNavigationComponentModelProtocol {
    var stack: SkieSwiftStateFlow<ChildStack<HomeConfig, HomeChild>> { get }
    var sheetItem: DecomposeSlotItem<HomeSheetChild>? { get set }

    func navigate(_ path: [ChildCreated<HomeConfig, HomeChild>])
}

@Observable
final class HomeTabNavigationComponentModel: HomeTabNavigationComponentModelProtocol {

    // MARK: Public computed properties

    var sheetItem: DecomposeSlotItem<HomeSheetChild>? {
        get {
            guard let child = _sheet.child else {
                return nil
            }
            return DecomposeSlotItem(id: ObjectIdentifier(child), instance: child.instance)
        }
        // swiftlint:disable:next unused_setter_value
        set {
            // SwiftUI sets this to nil on dismiss; we delegate to KMP
            // instead of managing state locally.
            actions.onSheetDismissed()
        }
    }

    // MARK: Private stored properties

    private var _sheet: ChildSlot<HomeSheetConfig, HomeSheetChild>

    // MARK: Private @ObservationIgnored properties

    @ObservationIgnored let stack: SkieSwiftStateFlow<ChildStack<HomeConfig, HomeChild>>
    @ObservationIgnored private let actions: HomeNavHostActions
    @ObservationIgnored private var stateTask: Task<Void, Never>?

    // MARK: Init / Deinit

    init(_ component: HomeNavHost) {
        _sheet = component.sheet.value
        stack = component.stack
        actions = component.actions

        // Safe: SKIE's StateFlow replays the current value to new collectors,
        // so no emissions are lost between the synchronous read above and this Task.
        stateTask = Task { [weak self] in
            for await state in component.sheet {
                self?._sheet = state
            }
        }
    }

    deinit {
        stateTask?.cancel()
    }

    // MARK: Public functions

    func navigate(_ path: [ChildCreated<HomeConfig, HomeChild>]) {
        actions.navigate(newStack: path)
    }
}
