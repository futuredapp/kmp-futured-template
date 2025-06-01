import LinkPresentation
import SwiftUI

struct ShareSheet: UIViewControllerRepresentable {
    let activityItems: [ShareSheetItem]

    func makeUIViewController(context: Context) -> UIViewController {
        UIActivityViewController(activityItems: activityItems, applicationActivities: nil)
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

class ShareSheetItem: NSObject, UIActivityItemSource {
    let title: String
    let subtitle: String
    let item: Any

    init(item: Any, title: String = "mDevCamp", subtitle: String = "By FTRD with 🖤") {
        self.title = title
        self.subtitle = subtitle
        self.item = item
        super.init()
    }

    func activityViewControllerPlaceholderItem(_ activityViewController: UIActivityViewController) -> Any {
        item
    }

    func activityViewController(_ activityViewController: UIActivityViewController, itemForActivityType activityType: UIActivity.ActivityType?) -> Any? {
        item
    }

    func activityViewControllerLinkMetadata(_ activityViewController: UIActivityViewController) -> LPLinkMetadata? {
        let metadata = LPLinkMetadata()
        metadata.title = title
        metadata.imageProvider = .init(object: UIImage(named: "AppIcon")!)
        metadata.originalURL = .init(fileURLWithPath: subtitle)
        return metadata
    }
}
