import SwiftUI

private struct AppFontModfier: ViewModifier {
    let appFont: AppFont
    let color: Color

    func body(content: Content) -> some View {
        content
            .font(appFont.font)
            .foregroundStyle(color)
    }
}

extension View {
    func appFont(_ appFont: AppFont, color: Color? = nil) -> some View {
        modifier(AppFontModfier(appFont: appFont, color: color ?? appFont.color))
    }
}
