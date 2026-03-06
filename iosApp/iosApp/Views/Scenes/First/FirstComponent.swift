import SwiftUI

struct FirstComponent<Model: FirstComponentModelProtocol>: View {
    @State var model: Model

    var body: some View {
        VStack(spacing: 10) {
            Text(model.counter)
            Text(model.createdAt)
            if let randomPerson = model.randomPerson {
                Text(randomPerson)
                    .multilineTextAlignment(.center)
            }
            Button(Localizable.first_screen_button.localized, action: model.onNext)
                .buttonStyle(.borderedProminent)
        }
        .navigationTitle(Localizable.first_screen_title.localized)
        .alert(model.alertText, isPresented: $model.isAlertVisible) {
            Button(Localizable.generic_close.localized) {
                model.isAlertVisible = false
            }
        }
    }
}

#if DEBUG
#Preview {
    NavigationStack {
        FirstComponent(model: FirstComponentModelMock())
    }
}
#endif
