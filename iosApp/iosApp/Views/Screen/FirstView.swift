import shared
import SwiftUI

struct FirstView: View {

    @StateObject @KotlinStateFlow private var viewState: FirstViewState
    private let actions: FirstScreenActions
    private let events: SkieSwiftFlow<FirstUiEvent>

    @State private var alertVisible: Bool = false
    @State private var alertText: String = ""

    init(_ screen: FirstScreen) {
        self._viewState = .init(screen.viewState)
        self.actions = screen.actions
        self.events = screen.events
    }

    var body: some View {
        VStack(spacing: 10) {
            Text(viewState.text.localized())
            Button("Go to second screen", action: actions.onNext).buttonStyle(.borderedProminent)
            Button("Go back", action: actions.onBack)
        }
        .navigationTitle("First screen")
        .eventsEffect(for: events) { event in
            switch onEnum(of: event) {
            case .showToast(let event):
                alertText = event.text
                alertVisible = true
            }
        }
        .alert(alertText, isPresented: $alertVisible) {
            Button("Close") { alertVisible = false }
        }
    }
}
