import SwiftUI

private struct AppBackgroundModifier: ViewModifier {
    let showColors: Bool
    let offset: CGSize

    func body(content: Content) -> some View {
        content
            .background {
                AppBackground(showColors: showColors, offset: offset)
            }
    }
}

struct AppBackground: View {
    let showColors: Bool
    let offset: CGSize

    init(showColors: Bool, offset: CGSize = .zero) {
        self.showColors = showColors
        self.offset = offset
    }

    var body: some View {
        Color(.appBlack)
            .frame(maxWidth: .infinity, maxHeight: .infinity)
            .ignoresSafeArea()
            .overlay {
                if showColors {
                    BlurredColorsView()
                        .frame(maxHeight: .infinity, alignment: .top)
                        .offset(offset)
                }
            }
    }
}

extension View {
    func appBackground(showColors: Bool, offset: CGSize = .zero) -> some View {
        modifier(AppBackgroundModifier(showColors: showColors, offset: offset))
    }
}

#Preview {
    AppBackground(showColors: true, offset: .init(width: 0, height: -200))
}
