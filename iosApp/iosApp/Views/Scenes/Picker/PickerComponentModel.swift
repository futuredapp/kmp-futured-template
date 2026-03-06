import KMP
import Observation

struct PickerItem: Identifiable {
    let id: String
}

protocol PickerComponentModelProtocol: AnyObject {
    var isLoading: Bool { get }
    var items: [PickerItem] { get }

    func onPick(item: PickerItem)
    func onDismiss()
}

@Observable
final class PickerComponentModel: PickerComponentModelProtocol {
    var isLoading: Bool {
        viewState.isLoading
    }
    var items: [PickerItem] {
        viewState.items.map {
            PickerItem(id: $0.localized())
        }
    }

    private var viewState: PickerState

    @ObservationIgnored private let actions: PickerScreenActions
    @ObservationIgnored private var stateTask: Task<Void, Never>?

    init(_ screen: PickerScreen) {
        viewState = screen.viewState.value
        actions = screen.actions
        stateTask = Task { [weak self] in
            for await state in screen.viewState {
                self?.viewState = state
            }
        }
    }

    deinit {
        stateTask?.cancel()
    }

    func onPick(item: PickerItem) {
        actions.onPick(item: item.id)
    }

    func onDismiss() {
        actions.onDismiss()
    }
}

#if DEBUG
@Observable
final class PickerComponentModelMock: PickerComponentModelProtocol {
    var isLoading = false
    var items: [PickerItem] = [
        PickerItem(id: "Apple"),
        PickerItem(id: "Banana"),
        PickerItem(id: "Cherry")
    ]

    func onPick(item: PickerItem) {
        print("Picked: \(item.id)")
    }

    func onDismiss() {
        print("Dismiss tapped")
    }
}
#endif
