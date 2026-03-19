import SwiftUI

struct ThirdComponent<Model: ThirdComponentModelProtocol>: View {
    @State var model: Model

    var body: some View {
        Text(model.text)
            .navigationTitle(Localizable.third_screen_title.localized)
    }
}

#if DEBUG
#Preview {
    NavigationStack {
        ThirdComponent(model: ThirdComponentModelMock())
    }
}
#endif
