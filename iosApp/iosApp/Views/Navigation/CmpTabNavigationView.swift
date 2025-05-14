import KMP
import SwiftUI

struct CmpTabNavigationView: View {

    private let stack: SkieSwiftStateFlow<ChildStack<CmpConfig, CmpChild>>
    private let actions: CmpNavHostActions

    init(_ component: CmpNavHost) {
        self.stack = component.stack
        self.actions = component.actions
    }

    var body: some View {
        DecomposeNavigationStack(
            kotlinStack: stack,
            setPath: actions.navigate
        ) { child in
            switch onEnum(of: child) {
            case .form(let enty):
                FormMultiplatformView(FormViewModel(enty.screen))
            case .interopCheck(let entry):
                InteropCheckMultiplatformView(InteropCheckViewModel(entry.screen))
            }
        }
    }
}
