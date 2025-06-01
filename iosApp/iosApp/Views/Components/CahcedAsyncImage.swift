import KMP
import SwiftUI

struct CachedAsyncImage: View {
    @State private var cachedImage: Image?
    let id: String
    let url: String
    var contentMode: ContentMode = .fill

    private func loadCachedImage(id: String) {
        self.cachedImage = FileService.shared.loadImage(id: id).flatMap { Image(uiImage: $0) }
    }

    private func storeCacheImage(image: UIImage) {
        FileService.shared.store(image: image, id: id)
    }

    var body: some View {
        content
            .task(priority: .background) {
                loadCachedImage(id: id)
            }
            .onChange(of: id) { newValue in
                loadCachedImage(id: newValue)
            }
    }

    @ViewBuilder private var content: some View {
        if let cachedImage {
            cachedImage
                .resizable()
                .aspectRatio(contentMode: contentMode)
        } else {
            remoteImage
         }
    }

    private var remoteImage: some View {
        RemoteImageView(stringURL: url, contentMode: contentMode) { image in
            storeCacheImage(image: image)
        }
    }
}
