import SwiftUI

struct SecondComponent<Model: SecondComponentModelProtocol>: View {
    @State var model: Model

    var body: some View {
        HStack(spacing: 10) {
            Button(Localizable.second_screen_button_fruit.localized, action: model.onPickFruit)
                .buttonStyle(.bordered)
            Button(Localizable.second_screen_button_veggie.localized, action: model.onPickVeggie)
                .buttonStyle(.bordered)
        }
        .navigationTitle(Localizable.second_screen_title.localized)
    }
}

#if DEBUG
#Preview {
    NavigationStack {
        SecondComponent(model: SecondComponentModelMock())
    }
}
#endif
