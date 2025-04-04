import KMP
import SwiftUI

/**
 This view displays Decompose navigation stack from KMP.
 
 It simplifies navigation stack implementation across application by abstracting away boilerplate needed to set up
 native `NavigationStack` view with Decompose stack stored in KMP.
 */
struct DecomposeNavigationStack<
    Child: ChildCreated<Destination, Entry>,
    Destination: AnyObject,
    Entry: AnyObject,
    Content: View
>: View {
    @StateObject @KotlinStateFlow private var kotlinStack: ChildStack<Destination, Entry>
    private let setPath: ([Child]) -> Void
    @ViewBuilder private let content: (Entry) -> Content

    private var swiftStack: [Child] {
        guard let swiftStack = kotlinStack.items as? [Child] else {
            fatalError("Kotlin navigation stack can't be converted to Swift stack.")
        }
        return swiftStack
    }

    private var path: Binding<Array<Child>.SubSequence> {
        .init {
            swiftStack.dropFirst()
        } set: {
            guard let rootItem = swiftStack.first else {
                fatalError("Navigation stack is inconsistent. It should have at least one item but is empty.")
            }
            setPath([rootItem] + $0)
        }
    }

    init(
        kotlinStack: SkieSwiftStateFlow<ChildStack<Destination, Entry>>,
        setPath: @escaping ([Child]) -> Void,
        @ViewBuilder content: @escaping (Entry) -> Content
    ) {
        self._kotlinStack = .init(kotlinStack)
        self.setPath = setPath
        self.content = content
    }

    var body: some View {
        NavigationStack(path: path) {
            if let firstEntry = swiftStack.first?.instance {
                content(firstEntry)
                    .navigationDestination(for: Child.self) { child in
                        content(child.instance)
                    }
            }
        }
    }
}
