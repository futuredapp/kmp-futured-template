import KMP
import SwiftUI

struct HomeTabNavigationView: View {

    private let stack: SkieSwiftStateFlow<ChildStack<HomeConfig, HomeChild>>
    private let actions: HomeNavHostActions

    init(_ component: HomeNavHost) {
        self.stack = component.stack
        self.actions = component.actions
    }

    var body: some View {
        DecomposeNavigationStack(
            kotlinStack: stack,
            setPath: actions.navigate
        ) { child in
            switch onEnum(of: child) {
            case .first(let entry):
                FirstView(FirstViewModel(entry.screen))
            case .second(let entry):
                SecondView(SecondViewModel(entry.screen))
            case .third(let entry):
                ThirdView(ThirdViewModel(entry.screen))
            }
        }
    }
}
