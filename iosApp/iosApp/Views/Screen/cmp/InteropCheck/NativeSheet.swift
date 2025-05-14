import SwiftUI

public enum NativeSheet: Identifiable {
    public var id: String {
        switch self {
        case .webUrl(let url):
            "webPayment_\(url.absoluteString)"
        }
    }
    case webUrl(url: URL)
}
