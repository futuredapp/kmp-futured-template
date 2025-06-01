import SwiftUI

struct BluredColorsView: View {
    @State private var scale: CGFloat = 1
    private let shouldOffest: Bool

    init(shouldOffest: Bool = true) {
        self.shouldOffest = shouldOffest
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
        .offset(y: shouldOffest ? -UIScreen.main.bounds.height / 8 : 0)
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
    BluredColorsView()
}
