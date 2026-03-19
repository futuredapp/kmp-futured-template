import KMP
import SwiftUI

struct HomeTabNavigationView: View {
    @State private var sheet: StateFlowObserver<ChildSlot<HomeSheetConfig, HomeSheetChild>>

    private let stack: SkieSwiftStateFlow<ChildStack<HomeConfig, HomeChild>>
    private let actions: HomeNavHostActions

    init(_ component: HomeNavHost) {
        _sheet = State(wrappedValue: StateFlowObserver(component.sheet))
        stack = component.stack
        actions = component.actions
    }

    var body: some View {
        DecomposeNavigationStack(
            kotlinStack: stack,
            setPath: actions.navigate
        ) { child in
            switch onEnum(of: child) {
            case let .first(entry):
                FirstComponent(model: FirstComponentModel(entry.screen))
            case let .second(entry):
                SecondComponent(model: SecondComponentModel(entry.screen))
            case let .third(entry):
                ThirdComponent(model: ThirdComponentModel(entry.screen))
            }
        }
        .sheet(
            isPresented: .init(
                get: { sheet.value.child != nil },
                set: { _ in
                    actions.onSheetDismissed()
                }
            )
        ) {
            if let child = sheet.value.child?.instance {
                switch onEnum(of: child) {
                case let .picker(instance):
                    PickerComponent(model: PickerComponentModel(instance.screen))
                        .presentationDetents(.init([.medium]))
                }
            }
        }
    }
}
