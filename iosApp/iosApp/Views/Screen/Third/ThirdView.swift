import KMP
import SwiftUI

struct ThirdView<ViewModel: ThirdViewModelProtocol>: View {
    private let viewModel: ViewModel

    @State private var showShareSheet = false

    init(_ viewModel: ViewModel) {
        self.viewModel = viewModel
    }

    var body: some View {
        wrappedContent
            .navigationBarTitleDisplayMode(.inline)
    }

    @ViewBuilder private var wrappedContent: some View {
        StateView(state: viewModel.state) {
            content
                .sheet(isPresented: $showShareSheet) {
                    if let loadedImage = viewModel.loadImage() {
                        ShareSheet(activityItems: [ShareSheetItem(item: loadedImage)])
                    }
                }
        }
    }

    private var content: some View {
        VStack(spacing: 0) {
            asyncImage
                .padding(.vertical, 20)
        }
        .toolbar {
            ToolbarItem(placement: .topBarTrailing) {
                Button {
                    showShareSheet = true
                } label: {
                    Image(systemName: "square.and.arrow.up")
                        .foregroundColor(Color(.appWhite))
                }
            }
        }
        .toolbarRole(.editor)
    }

    private var asyncImage: some View {
        CachedAsyncImage(id: viewModel.avatar.id.description, url: viewModel.avatar.image)
            .frame(width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height * 0.6)
    }
}
