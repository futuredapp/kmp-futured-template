import SwiftUI
import shared

struct FirstView: View {
    
    @ObservedObject @KotlinStateFlow private var viewState: FirstViewState
    private let actions: FirstScreenActions
    private let events: SkieSwiftFlow<FirstUiEvent>
    private let bindings: FirstScreenBindings
    
    @State private var alertVisible: Bool = false
    @State private var alertText: String = ""
    
    init(_ screen: FirstScreen) {
        self._viewState = .init(screen.viewState)
        self.actions = screen.actions
        self.events = screen.events
        self.bindings = screen.bindings
    }
    
    var body: some View {
        VStack(spacing: 10) {
            Text(viewState.text)
            Button("Go to second screen", action: actions.onNext).buttonStyle(.borderedProminent)
            Button("Go back", action: actions.onBack)
            
            TextField("Test Text Field", text: bindings.textField.swiftBinding())
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
            Button("Close", action: {alertVisible = false})
        }
    }
}

extension KotlinStringBinding {
    func swiftBinding() -> Binding<String> {
        return Binding(
            get: self.get,
            set: self.set
        )
    }
}
