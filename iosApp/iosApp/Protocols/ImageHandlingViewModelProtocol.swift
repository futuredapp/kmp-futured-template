import SwiftUI

protocol ImageHandlingViewModelProtocol: DynamicProperty {
    var isImageConverting: Bool { get }
    var image: UIImage? { get set }
    var isLoading: Bool { get }

    func tookSelfie(image: UIImage)
    func toggleImageConversion(converting: Bool)
}
