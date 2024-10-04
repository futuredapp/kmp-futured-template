import shared
import SwiftUI

struct TabBNavigationView: View {

    private let stack: SkieSwiftStateFlow<ChildStack<TabBDestination, TabBNavEntry>>
    private let actions: TabBNavigationActions

    init(_ component: TabBNavigation) {
        self.stack = component.stack
        self.actions = component.actions
    }

    var body: some View {
        DecomposeNavigationStack(
            kotlinStack: stack,
            setPath: actions.iosPopTo
        ) { entry in
            switch onEnum(of: entry) {
            case .first(let entry):
                FirstView(FirstViewModel(entry.instance))
            case .second(let entry):
                SecondView(SecondViewModel(entry.instance))
            case .third(let entry):
                ThirdView(ThirdViewModel(entry.instance))
            case .secret(let entry):
                SecretView(SecretViewModel(entry.instance))
            }
        }
    }
}
