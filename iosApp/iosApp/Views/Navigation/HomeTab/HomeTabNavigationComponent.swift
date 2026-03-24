import KMP
import SwiftUI

struct HomeTabNavigationComponent<Model: HomeTabNavigationComponentModelProtocol>: View {
    @State var model: Model

    var body: some View {
        DecomposeNavigationStack(
            kotlinStack: model.stack,
            setPath: model.navigate
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
        .sheet(item: $model.sheetItem) { item in
            switch onEnum(of: item.instance) {
            case let .picker(instance):
                PickerComponent(model: PickerComponentModel(instance.screen))
                    .presentationDetents(.init([.medium]))
            }
        }
    }
}
