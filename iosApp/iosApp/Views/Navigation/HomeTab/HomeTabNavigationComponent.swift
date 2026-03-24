import KMP
import SwiftUI

struct HomeTabNavigationComponent: View {
    @State var model: HomeTabNavigationComponentModel

    var body: some View {
        DecomposeNavigationStack(
            kotlinStack: model.stack,
            setPath: model.actions.navigate
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
                get: { model.sheet.child != nil },
                set: { _ in
                    model.onSheetDismissed()
                }
            )
        ) {
            if let child = model.sheet.child?.instance {
                switch onEnum(of: child) {
                case let .picker(instance):
                    PickerComponent(model: PickerComponentModel(instance.screen))
                        .presentationDetents(.init([.medium]))
                }
            }
        }
    }
}
