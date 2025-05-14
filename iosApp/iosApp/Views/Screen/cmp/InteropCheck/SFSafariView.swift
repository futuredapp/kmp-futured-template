import SafariServices
import SwiftUI

public struct SFSafariView: UIViewControllerRepresentable {
    let url: URL

    public init(url: URL) {
        self.url = url
    }

    public func makeUIViewController(context: UIViewControllerRepresentableContext<Self>) -> SFSafariViewController {
        let controller = SFSafariViewController(url: url)
        return controller
    }

    public func updateUIViewController(_ uiViewController: SFSafariViewController, context: UIViewControllerRepresentableContext<SFSafariView>) {}
}
