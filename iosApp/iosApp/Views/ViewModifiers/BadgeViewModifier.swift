import SwiftUI

private struct BadgeViewModifier: ViewModifier {
    func body(content: Content) -> some View {
        content
            .frame(maxWidth: .infinity)
            .frame(height: UIScreen.main.bounds.height * 0.6)
            .background(
                Color(.appDark)
            )
            .cornerRadius(15)
            .padding(1)
            .background(
                RoundedRectangle(cornerRadius: 15.0)
                    .fill(linearGradient2)
            )
    }

    private var linearGradient2: LinearGradient {
        LinearGradient(
            gradient: Gradient(colors: [.white.opacity(0.32), .white.opacity(0.05)]),
            startPoint: .topLeading,
            endPoint: .bottomTrailing
        )
    }
}

extension View {
    func badgeViewStyle() -> some View {
        self.modifier(BadgeViewModifier())
    }
}
