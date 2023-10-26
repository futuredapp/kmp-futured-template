import SwiftUI
import shared

struct ThirdView: View {
    
    @ObservedObject @KotlinState private var viewState: ThirdViewState
    private let actions: ThirdScreenActions
    
    init(_ screen: ThirdScreen) {
        self._viewState = .init(screen.viewState)
        self.actions = screen.actions
    }
    
    var body: some View {
        VStack(spacing: 10) {
            Text(viewState.text)
            Button("Go back", action: actions.onBack)
        }
        .navigationTitle("Third screen")
    }
}
