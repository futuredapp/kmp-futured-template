import SwiftUI

struct ProfileComponent<Model: ProfileComponentModelProtocol>: View {
    @State var model: Model

    var body: some View {
        VStack(spacing: 10) {
            Button(Localizable.generic_sign_out.localized, action: model.onLogoutClick)
            Button(Localizable.profile_navigate_to_third.localized, action: model.onThirdClick)
        }
        .navigationTitle(Localizable.profile_screen_title.localized)
    }
}

#if DEBUG
#Preview {
    NavigationStack {
        ProfileComponent(model: ProfileComponentModelMock())
    }
}
#endif
