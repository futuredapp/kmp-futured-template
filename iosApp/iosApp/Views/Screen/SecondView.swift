import shared
import SwiftUI

struct SecondView: View {

    @StateObject @KotlinStateFlow private var viewState: SecondViewState
    private let actions: SecondScreenActions

    init(_ screen: SecondScreen) {
        self._viewState = .init(screen.viewState)
        self.actions = screen.actions
    }

    var body: some View {
        VStack(spacing: 10) {
            Text(viewState.text.localized())
            Button("Go to third screen", action: actions.onNext).buttonStyle(.borderedProminent)
            Button("Go back", action: actions.onBack)
        }
        .navigationTitle(Localizable.second_screen_title.localized)
    }
}
