import KMP
import SwiftUI

protocol SecondViewModelProtocol: DynamicProperty {
    var isLoading: Bool { get }
      var styles: [AvatarStyle] { get }
      var selectedStyle: AvatarStyle? { get }
      var state: StateViewState { get }

      func select(style: AvatarStyle)
      func cancel()
      func generate()
}

struct SecondViewModel {
    @StateObject @KotlinStateFlow private var viewState: SecondViewState
    private let actions: SecondScreenActions

    init(_ screen: SecondScreen) {
        _viewState = .init(screen.viewState)

        actions = screen.actions
    }
}

extension SecondViewModel: SecondViewModelProtocol {
    var styles: [AvatarStyle] {
           viewState.styles
       }

       var isLoading: Bool {
           viewState.isLoading
       }

       var selectedStyle: AvatarStyle? {
           viewState.selectedStyle
       }

       var state: StateViewState {
           StateViewState(dialog: viewState.uiDialog, error: viewState.error, retryAction: actions.onRetry)
       }

       func select(style: AvatarStyle) {
           actions.onStyle(style: style)
       }

       func cancel() {
           actions.onCancel()
       }

       func generate() {
           actions.onGenerate()
       }
}

extension AvatarStyle {
    var previewUrl: URL? {
        URL(string: preview)
    }
}
