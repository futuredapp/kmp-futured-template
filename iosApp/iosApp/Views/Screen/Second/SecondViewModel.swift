import shared
import SwiftUI

protocol SecondViewModelProtocol: DynamicProperty {
    var text: String { get }
    var picker: shared.Picker? { get }

    func onPickFruit()
    func onPickVeggie()
    func onPickerDismissed()
}

struct SecondViewModel {
    @StateObject @KotlinStateFlow private var viewState: SecondViewState
    @StateObject @KotlinStateFlow private var pickerSlot: ChildSlot<SecondScreenPickerType, shared.Picker>
    private let actions: SecondScreenActions

    init(_ screen: SecondScreen) {
        _viewState = .init(screen.viewState)
        _pickerSlot = .init(screen.picker)
        actions = screen.actions
    }
}

extension SecondViewModel: SecondViewModelProtocol {

    var text: String {
        viewState.text.localized()
    }

    var picker: shared.Picker? {
        pickerSlot.child?.instance
    }

    func onPickFruit() {
        actions.onPickFruit()
    }

    func onPickVeggie() {
        actions.onPickVeggie()
    }

    func onPickerDismissed() {
        actions.onPickerDismissed()
    }
}
