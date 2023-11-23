import shared
import SwiftUI

struct ThirdView: View {

    @ObservedObject @KotlinStateFlow private var viewState: ThirdViewState
    private let actions: ThirdScreenActions

    init(_ screen: ThirdScreen) {
        self._viewState = .init(screen.viewState)
        self.actions = screen.actions
    }

    var body: some View {
        VStack(spacing: 10) {
            Text(viewState.text.localized())
            Button("Go back", action: actions.onBack)
        }
        .navigationTitle(Localizable.third_screen_text.localized)
    }
}
