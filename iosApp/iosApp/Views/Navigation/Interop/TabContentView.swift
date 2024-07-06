import shared
import SwiftUI

/**
 This view is used to display content inside native `TabView` in a KMP-compatible manner.
 */
struct TabContentView<
    Entry: SignedInNavEntry,
    Content: View
>: View {

    let navEntry: Entry?
    let navigationTab: NavigationTab

    @ViewBuilder private let content: (Entry) -> Content

    init(
        ofNavigationEntry entry: Entry?,
        forNavigationTab tab: NavigationTab,
        @ViewBuilder content: @escaping (Entry) -> Content
    ) {
        self.navEntry = entry
        self.navigationTab = tab
        self.content = content
    }

    var body: some View {
        ZStack {
            if let entry = navEntry {
                // The .id() modifier is very important, tells TabView to render again whenever underlying navEntry is updated.
                // I literally spent entire weekend figuring this out 🔫.
                content(entry).id(entry.iosViewId)
            }
        }
        .tag(navigationTab)
        .tabItem {
            Label {
                Text(navigationTab.title.localized())
            } icon: {
                Image(systemName: navigationTab.icon())
                    .renderingMode(.template)
            }
        }
    }
}

extension NavigationTab {
    func icon() -> String {
        switch self {
        case .a:
            "house.fill"
        case .b:
            "plus"
        case .c:
            "person.crop.circle"
        }
    }
}
