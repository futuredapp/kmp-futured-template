import KMP
import SwiftUI

struct FirstView<ViewModel: FirstViewModelProtocol & ImageHandlingViewModelProtocol>: View {
    private let viewModel: ViewModel

    @State var isCameraPresented: Bool = false

    init(_ viewModel: ViewModel) {
        self.viewModel = viewModel
    }

    var body: some View {
        ScrollView {
            wrappedContent
                .padding(.bottom, 25)
        }
        .refreshable(action: viewModel.refresh)
        .appBackground(showColors: true)
        .navigationBarTitleDisplayMode(.inline)
    }

    private var wrappedContent: some View {
        StateView(state: viewModel.state) {
            content.task {
                viewModel.loadAvatars()
            }
        }
    }

    private var content: some View {
           VStack(spacing: 0) {
               avatar
                   .padding(.bottom, 30)
               createNewAvatar
                   .padding(.bottom, 48)
               gallery
           }
           .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .top)
           .padding(.horizontal, 20)
           .padding(.top, 20)
           .toolbar {
               ToolbarItem(placement: .topBarTrailing) {
                   Button {
                       isCameraPresented = true
                   } label: {
                       Text("Retake photo")
                           .appFont(.description4, color: Color(.appWhite))
                   }
               }
           }
           .sheet(isPresented: $isCameraPresented) {
               SelfieView(
                              selection: photoBinding,
                              cameraPermissionAlertConfiguration: .init(title: "", message: "", dismissButtonTitle: "", settingsButtonTitle: "")
                          )
           }
       }

       var photoBinding: Binding<UIImage?> {
           Binding {
               viewModel.image
           } set: { image in
               if let image = image {
                   DispatchQueue.main.async {
                       self.viewModel.toggleImageConversion(converting: true)
                   }
                   DispatchQueue.global(qos: .background).async {
                       self.viewModel.tookSelfie(image: image)

                       DispatchQueue.main.async {
                           self.viewModel.toggleImageConversion(converting: false)
                       }
                   }
               }
           }
       }

       private var avatar: some View {
           CachedAsyncImage(id: viewModel.userPhotoId ?? "", url: viewModel.userPhoto ?? "")
               .cornerRadius(8)
               .overlay {
                   if viewModel.userPhotoLoading {
                       ZStack {
                           Color(.appBlack).opacity(0.5)
                           LoadingView()
                       }
                   }
               }
               .overlay {
                   RoundedRectangle(cornerRadius: 8)
                       .stroke(Color(.appWhite), lineWidth: 0.5)
               }
       }

       private var placeholder: some View {
           ZStack {
               Color(.appDark)
               AvatarPlaceholderView()
                   .frame(width: 128, height: 128)
           }
           .frame(height: 400)
           .cornerRadius(8)
           .overlay {
               RoundedRectangle(cornerRadius: 8)
                   .stroke(Color(.appWhite), lineWidth: 0.5)
           }
       }

       private var createNewAvatar: some View {
           AppButton(text: "\(Localizable.create_new_avatar.localized)", action: viewModel.createNewAvatar)
       }

       @State var avatarsPadding: CGFloat = .zero
       private var gallery: some View {
           VStack(spacing: 0) {
               GradientColoredText {
                   Text(Localizable.gallery_title.localized)
                       .appFont(.azHeading)
               }
               .frame(maxWidth: .infinity, alignment: .leading)
               .padding(.bottom, 16)
               LazyVStack(alignment: .leading, spacing: 12) {
                   let chunked = viewModel.avatars.chunked(into: 3)
                   ForEach(Array(chunked.enumerated()), id: \.offset) { _, avatarsChunk in
                       HStack(spacing: avatarsPadding) {
                           ForEach(avatarsChunk, id: \.id) { avatar in
                               avatarView(avatar)
                                   .readSize { size in
                                       if avatarsPadding == .zero {
                                           avatarsPadding = (UIScreen.main.bounds.width - (3 * size.width ) - 40) / 2
                                       }
                                   }
                           }
                       }
                   }
               }
           }
       }

       func avatarView(_ avatar: Avatar) -> some View {
           Group {
               if avatar.status == .done {
                   CachedAsyncImage(id: avatar.id.description + "-preview", url: avatar.preview)
                       .frame(width: 110, height: 110)
                       .cornerRadius(8)
               } else {
                   ZStack {
                       CachedAsyncImage(id: avatar.style.cacheId, url: avatar.style.preview)
                           .frame(width: 110, height: 110)
                           .cornerRadius(8)

                       Color(.appBlack)
                           .opacity(0.8)
                           .cornerRadius(8)
                       Text(avatar.status == .failed ? "Failed" : Localizable.in_progress_title.localized)
                           .appFont(.textBold)
                   }
                   .frame(width: 110, height: 110)
               }
           }
           .onTapGesture {
               viewModel.select(avatar: avatar)
           }
       }
}

extension AvatarStyle {
    var cacheId: String {
        "\(id)-style"
    }
}

extension Array {
    func chunked(into size: Int) -> [[Element]] {
       stride(from: 0, to: count, by: size).map {
            Array(self[$0 ..< Swift.min($0 + size, count)])
       }
    }
}
