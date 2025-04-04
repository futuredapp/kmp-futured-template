import KMP
import SwiftUI

protocol SecondViewModelProtocol: DynamicProperty {
    var picker: KMP.Picker? { get }

    func onPickFruit()
    func onPickVeggie()
    func onPickerDismissed()
}

struct SecondViewModel {
    @StateObject @KotlinStateFlow private var viewState: SecondViewState
    @StateObject @KotlinStateFlow private var pickerSlot: ChildSlot<SecondScreenPickerType, KMP.Picker>
    private let actions: SecondScreenActions

    init(_ screen: SecondScreen) {
        _viewState = .init(screen.viewState)
        _pickerSlot = .init(screen.picker)
        actions = screen.actions
    }
}

extension SecondViewModel: SecondViewModelProtocol {

    var picker: KMP.Picker? {
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
