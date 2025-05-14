import KMP
import SafariServices
import SwiftUI

struct InteropCheckMultiplatformComposeView<InteropCheckScreen: KMP.InteropCheckScreen>: UIViewControllerRepresentable {

    private let screen: InteropCheckScreen

    init(_ screen: InteropCheckScreen) {
        self.screen = screen
    }

    func makeUIViewController(context: Context) -> UIViewController {
        ComposeMultiplatformInteropCheckScreenControllerKt.ComposeMultiplatformInteropCheckScreenController(screen: screen, nativePart: webViewFactory)
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}

    func webViewFactory() -> UIViewController {
        let nativeWebView = WebView(URL(string: screen.viewState.value.url)!).ignoresSafeArea(.all)
        return UIHostingController(rootView: nativeWebView)
    }
}

struct InteropCheckMultiplatformView<ViewModel: InteropCheckViewModelProtocol>: View {
    private let viewModel: ViewModel

    init(_ viewModel: ViewModel) {
        self.viewModel = viewModel
    }

    var body: some View {
        InteropCheckMultiplatformComposeView(viewModel.screen)
            .ignoresSafeArea(.all) // we will use compose Window Insets API
            .sheet(item: viewModel.sheet.projectedValue) { sheet in
                switch sheet {
                case .webUrl(let url):
                    SFSafariView(url: url).ignoresSafeArea(edges: .bottom)
                }
            }.onAppear {
                Task {
                    await viewModel.onAppear()
                }
            }
    }
}

struct SafariView: UIViewControllerRepresentable {
    let url: URL

    func makeUIViewController(context: UIViewControllerRepresentableContext<SafariView>) -> SFSafariViewController {
        SFSafariViewController(url: url)
    }

    func updateUIViewController(_ uiViewController: SFSafariViewController, context: UIViewControllerRepresentableContext<SafariView>) {
    }
}
