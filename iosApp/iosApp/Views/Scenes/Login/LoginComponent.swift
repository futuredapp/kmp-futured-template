import SwiftUI

struct LoginComponent<Model: LoginComponentModelProtocol>: View {
    @State var model: Model

    var body: some View {
        VStack(spacing: 10) {
            Text(Localizable.login_screen_title.localized)
            Button(Localizable.generic_sign_in.localized, action: model.onLoginClick)
                .buttonStyle(.borderedProminent)
        }
    }
}

#if DEBUG
#Preview {
    LoginComponent(model: LoginComponentModelMock())
}
#endif
