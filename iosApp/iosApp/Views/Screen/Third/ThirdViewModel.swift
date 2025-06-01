import KMP
import SwiftUI

protocol ThirdViewModelProtocol: DynamicProperty {
    var state: StateViewState { get }
      var avatar: Avatar { get }
      var shouldChange: State<Bool> { get }
      var isEndScreen: Bool { get }

      func loadImage() -> UIImage?
}

struct ThirdViewModel {
    @StateObject @KotlinStateFlow private var viewState: ThirdViewState
    private let actions: ThirdScreenActions
    let shouldChange: State<Bool> = .init(initialValue: false)

    init(_ screen: ThirdScreen) {
        _viewState = .init(screen.viewState)
        actions = screen.actions
    }
}

extension ThirdViewModel: ThirdViewModelProtocol {
    var state: StateViewState {
            StateViewState(dialog: viewState.uiDialog, error: nil, retryAction: nil)
        }

        var avatar: Avatar {
            viewState.avatar
        }

        var isEndScreen: Bool {
            false
        }
}

extension ThirdViewModelProtocol {
    func loadImage() -> UIImage? {
        if let image = FileService.shared.loadImage(id: avatar.id.description) {
            return image
        }
        return FileService.shared.loadImage(id: avatar.id.description + "-preview")
    }
}
