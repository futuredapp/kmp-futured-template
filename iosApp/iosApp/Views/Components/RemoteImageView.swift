import SwiftUI

struct RemoteImageView: View {

    /// An iteration counter for downloading images and enforcing body calls.
    @State private var counter = 0

    private let stringURL: String?
    private let contentMode: ContentMode
    private let failureMessage: String?
    private let maxDownloadAttempts: Int
    private let imageAction: ((UIImage) -> Void)?

    init(
        stringURL: String?,
        contentMode: ContentMode = .fit,
        failureMessage: String? = nil,
        maxDownloadAttempts: Int = 3,
        imageAction: ((UIImage) -> Void)? = nil
    ) {
        self.stringURL = stringURL
        self.contentMode = contentMode
        self.failureMessage = failureMessage
        self.maxDownloadAttempts = maxDownloadAttempts
        self.imageAction = imageAction
    }

    var body: some View {
        if let stringURL = stringURL, let url = URL(string: stringURL) {
            if let cached = ImageCache[url] {
                Image(uiImage: cached)
                    .resizable()
                    .aspectRatio(contentMode: contentMode)
            } else {
                if counter >= maxDownloadAttempts {
                    failureContent()
                } else {
                    placeholderContent()
                        // This is called only once, not each time the counter is changed.
                        // But the body is called each time the counter is changed.
                        .onAppear {
                            downloadRemoteImage(from: url)
                        }
                }
            }
        } else {
            placeholderContent()
        }
    }
}

extension RemoteImageView {

    @ViewBuilder
    func placeholderContent() -> some View {
        AvatarPlaceholderView()
            .padding()
    }

    @ViewBuilder
    func failureContent() -> some View {
        if let failureMessage = failureMessage {
            Text(failureMessage)
                .font(.caption)
                .fontWeight(.medium)
        } else {
            placeholderContent()
        }
    }

    func downloadRemoteImage(from url: URL) {
        Task {
            while counter < maxDownloadAttempts {
                if let image = await downloadImage(from: url) {
                    DispatchQueue.main.async {
                        ImageCache[url] = image
                        if counter == 0 {
                            // In this case, we have to render the body twice because
                            // the wrapped counter must be changed, otherwise the body
                            // will not render and the placeholder will remain displayed.
                            reloadBody()
                        }
                        resetCounter()
                    }
                    break
                }
                DispatchQueue.main.async {
                    counter += 1
                }
            }
        }
    }

    func downloadImage(from url: URL) async -> UIImage? {

        do {
            let (data, _) = try await URLSession.shared.data(from: url)
            guard let uiImage = UIImage(data: data) else {
                return nil
            }
            imageAction?(uiImage)
            return uiImage
        } catch {
            return nil
        }
    }

    func reloadBody() {
        // Enforcing the body call.
        // The body uses cached images or a placeholder.
        counter += 1
    }

    func resetCounter() {
        counter = 0
    }
}

// MARK: - Image in-memory cache.

private class ImageCache {
    private static var cache: [URL: UIImage] = [:]

    static subscript(url: URL) -> UIImage? {
        get { ImageCache.cache[url] }
        set { ImageCache.cache[url] = newValue }
    }
}
