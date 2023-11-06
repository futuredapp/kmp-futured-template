import SwiftUI
import shared

struct SecondView: View {
    
    @ObservedObject @KotlinStateFlow private var viewState: SecondViewState
    private let actions: SecondScreenActions
    
    init(_ screen: SecondScreen) {
        self._viewState = .init(screen.viewState)
        self.actions = screen.actions
    }
    
    var body: some View {
        VStack(spacing: 10) {
            Text(viewState.text)
            Button("Go to third screen", action: actions.onNext).buttonStyle(.borderedProminent)
            Button("Go back", action: actions.onBack)
        }
        .navigationTitle("Second screen")
    }
}
