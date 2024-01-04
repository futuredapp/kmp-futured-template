import shared
import SwiftUI

struct SecretView: View {

    private let screen: SecretScreen

    init(_ screen: SecretScreen) {
        self.screen = screen
    }

    var body: some View {
        VStack(spacing: 10) {
            Text(screen.text.localized())
            Button("Go back", action: screen.goBack)
        }
        .navigationTitle(Localizable.secret_screen_title.localized)
    }
}
