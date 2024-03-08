// swiftlint:disable:this file_name
import shared
import SwiftUI

// swiftlint:disable:next identifier_name
let Localizable = MR.strings()

extension shared.StringResource {
    /**
     Localizes shared KMP String resource when accessed directly by `Localizable` object.
     */
    var localized: String {
        desc().localized()
    }
}

extension shared.ColorResource {
    /**
     Converts KMP Color resource to SwiftUI Color.
     */
    var color: SwiftUI.Color {
        Color(uiColor: getUIColor())
    }
}

extension shared.ImageResource {
    /**
     Converts KMP Image resource to SwiftUI Image.
     */
    var image: Image {
        let uiImage = toUIImage()
        assert(uiImage != nil, "ImageResource cannot be converted to UIImage")
        return Image(uiImage: uiImage ?? .init())
    }
}
