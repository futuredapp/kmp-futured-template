import KMP
import SwiftUI

protocol FirstViewModelProtocol: DynamicProperty {

    var state: StateViewState { get }
    var avatars: [Avatar] { get }
    var selectedAvatar: String? { get }
    var image: UIImage? { get set }
    var userPhoto: String? { get }
    var userPhotoId: String? { get }
    var userPhotoLoading: Bool { get }

    func createNewAvatar()
    func select(avatar: Avatar)
    func refresh() async
}

struct FirstViewModel: ImageHandlingViewModelProtocol {
    @StateObject @KotlinStateFlow private var viewState: FirstViewState
    private let actions: FirstScreenActions
    let events: SkieSwiftFlow<FirstUiEvent>

    @State var image: UIImage?
    @State var isImageConverting: Bool = false

    init(_ screen: FirstScreen) {
        _viewState = .init(screen.viewState)
        actions = screen.actions
        events = screen.events
    }
}

extension FirstViewModel: FirstViewModelProtocol {
    var state: StateViewState {
         StateViewState(dialog: viewState.uiDialog, error: viewState.error, retryAction: actions.onRetry)
     }

    var avatars: [Avatar] {
        viewState.avatars
    }

    var selectedAvatar: String? {
        viewState.selectedAvatar
    }

    var isLoading: Bool {
        viewState.isLoading
    }

    func createNewAvatar() {
        actions.onCreateNewAvatar()
    }

    func select(avatar: Avatar) {
        guard avatar.status != .inProgress else {
            return
        }
        actions.onAvatar(avatar: avatar)
    }

    func toggleImageConversion(converting: Bool) {
        isImageConverting = converting
    }

    var userPhoto: String? {
        viewState.photo
    }

    var userPhotoId: String? {
        guard let photo = viewState.photo, let photoUrl = URL(string: photo) else {
            return nil
        }

        return photoUrl.lastPathComponent
    }

    var userPhotoLoading: Bool {
        viewState.isPhotoLoading
    }

    func tookSelfie(image: UIImage) {
        guard !viewState.isPhotoLoading else {
            return
        }

        self.image = image

        guard let jpegData = image.jpegData(compressionQuality: 0.6) else { return }
        // swiftlint:disable:next force_unwrapping
        let swiftByteArray: [Int8] = jpegData.map { Int8(bitPattern: $0) }
        let kotlinByteArray: KotlinByteArray = KotlinByteArray(size: Int32(swiftByteArray.count))
        for (index, element) in swiftByteArray.enumerated() {
            kotlinByteArray.set(index: Int32(index), value: element)
        }

        actions.onRetakePhoto(imageName: "selfie", imageData: kotlinByteArray)
    }

    func refresh() async {
        guard !isLoading else { return }
        actions.onRetry()
        try? await Task.sleep(for: .seconds(3))
    }
}
