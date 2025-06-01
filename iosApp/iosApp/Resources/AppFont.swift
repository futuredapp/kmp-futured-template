import SwiftUI

enum AppFont {
    /// DINPro-Medium 28 px
    case heading1
    /// DINPro-Medium 16 px
    case heading2
    /// DINPro-Medium 22 px
    case heading3
    /// DINPro-Regular 24 px
    case description1
    /// DINPro-Regular 18 px
    case description2
    /// DINPro-Regular 17 px
    case description3
    /// DINPro-Regular 16 px
    case description4
    /// DINPro-Regular 12 px
    case nameLabel
    /// DINPro-Regular 14 px
    case text
    /// DINPro-Medium 12 px
    case textBold
    /// Azeret mono 13px
    case azHeading

    var font: Font {
        .custom(fontName, size: originalPointSize)
    }

    var color: Color {
        switch self {
        case .description4:
            return Color(.appWhite).opacity(0.6)
        default:
            return Color(.appWhite)
        }
    }

    private var fontName: String {
        switch self {
        case .heading1, .heading2, .heading3, .textBold:
            return "DINPro-Medium"
        case .description1, .description2, .description3, .description4, .text, .nameLabel:
            return "DINPro-Regular"
        case .azHeading:
            return "AzeretMono-Medium"
        }
    }

    private var originalPointSize: CGFloat {
        switch self {
        case .heading1:
            return 28
        case .heading2:
            return 16
        case .heading3:
            return 22
        case .description1:
            return 24
        case .description2:
            return 18
        case .description3:
            return 17
        case .description4:
            return 16
        case .azHeading:
            return 13
        case .nameLabel:
            return 14
        case .text, .textBold:
            return 12
        }
    }
}
