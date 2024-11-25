import shared
import SwiftUI

struct PickerItem: Identifiable {
    let id: String
}

protocol PickerViewModelProtocol: DynamicProperty {
    var isLoading: Bool { get }
    var items: [PickerItem] { get }

    func onPick(item: PickerItem)
    func onDismiss()
}

struct PickerViewModel {
    @StateObject @KotlinStateFlow private var viewState: PickerState
    private let actions: PickerActions

    init(_ screen: shared.Picker) {
        _viewState = .init(screen.viewState)
        actions = screen.actions
    }
}

extension PickerViewModel: PickerViewModelProtocol {

    var isLoading: Bool {
        viewState.isLoading
    }

    var items: [PickerItem] {
        viewState.items.map { id in
            PickerItem(id: id)
        }
    }

    func onPick(item: PickerItem) {
        actions.onPick(item: item.id)
    }

    func onDismiss() {
        actions.onDismiss()
    }
}
