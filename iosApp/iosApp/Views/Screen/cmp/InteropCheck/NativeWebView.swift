import Foundation
import SwiftUI
import WebKit

struct WebView: UIViewRepresentable {
    let url: URL

    init(_ url: URL) {
        self.url = url
    }

    func makeUIView(context: Context) -> WKWebView {
        let request = URLRequest(url: url)
        let webView = WKWebView()
        webView.load(request)
        return webView
    }

    func updateUIView(_ webView: WKWebView, context: Context) {    }
}
