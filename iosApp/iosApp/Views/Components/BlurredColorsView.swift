import SwiftUI

struct BlurredColorsView: View {
    @State private var scale: CGFloat = 1
    private let shouldOffset: Bool

    init(shouldOffset: Bool = true) {
        self.shouldOffset = shouldOffset
    }

    var body: some View {
        HStack {
            Ellipse()
                .fill(Color(.appOrange))
            Ellipse()
                .fill(Color(.appPurple))
        }
        .padding(20)
        .blur(radius: 75)
        .frame(maxHeight: UIScreen.main.bounds.height / 2)
        .offset(y: shouldOffset ? -UIScreen.main.bounds.height / 8 : 0)
        .scaleEffect(scale)
        .onAppear {
            withAnimation(
                .easeInOut(duration: 2)
                .repeatForever(autoreverses: true)
            ) {
                scale = scale == 1 ? 1.5 : 1
            }
        }
    }
}

#Preview {
    BlurredColorsView()
}
