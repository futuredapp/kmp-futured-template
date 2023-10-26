import SwiftUI
import shared

struct FirstView: View {
    
    @ObservedObject @KotlinState private var viewState: FirstViewState
    private let actions: FirstScreenActions
    
    init(_ screen: FirstScreen) {
        self._viewState = .init(screen.viewState)
        self.actions = screen.actions
    }
    
    var body: some View {
        VStack(spacing: 10) {
            Text(viewState.text)
            Button("Go to second screen", action: actions.onNext).buttonStyle(.borderedProminent)
            Button("Go back", action: actions.onBack)
        }
        .navigationTitle("First screen")
    }
}
