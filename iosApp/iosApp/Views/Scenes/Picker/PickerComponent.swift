import SwiftUI

struct PickerComponent<Model: PickerComponentModelProtocol>: View {
    @State var model: Model

    var body: some View {
        VStack(spacing: 10) {
            HStack(spacing: 10) {
                Text(Localizable.picker_title.localized).font(.headline)
                Button(Localizable.generic_close.localized, action: model.onDismiss)
            }
            if model.isLoading {
                ProgressView()
            } else {
                List {
                    ForEach(model.items) { item in
                        Button {
                            model.onPick(item: item)
                        } label: {
                            Text(item.id)
                                .frame(maxWidth: .infinity, alignment: .leading)
                                .contentShape(Rectangle())
                        }
                    }
                }
            }
        }
    }
}

#if DEBUG
#Preview {
    PickerComponent(model: PickerComponentModelMock())
}
#endif
