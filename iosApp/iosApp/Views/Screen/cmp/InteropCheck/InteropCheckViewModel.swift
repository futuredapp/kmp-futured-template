import KMP
import SwiftUI

protocol InteropCheckViewModelProtocol: DynamicProperty {
    var sheet: Binding<NativeSheet?> { get }
    var screen: InteropCheckScreen { get }

    func onAppear() async
}

struct InteropCheckViewModel {
    let screen: InteropCheckScreen

    private let events: SkieSwiftFlow<InteropCheckEvent>
    @State private var internalSheetUrl: NativeSheet?

    init(_ screen: InteropCheckScreen) {
        self.screen = screen
        self.events = screen.events
    }

    private func observeEvents() async {
        for await event in events {
            switch onEnum(of: event) {
            case .launchWebBrowser(let event):
                self.internalSheetUrl = NativeSheet.webUrl(url: URL(string: event.url)!)
            }
        }
    }

    func onAppear() async {
        await observeEvents()
    }
}

extension InteropCheckViewModel: InteropCheckViewModelProtocol {
    public var sheet: Binding<NativeSheet?> {$internalSheetUrl}
}
