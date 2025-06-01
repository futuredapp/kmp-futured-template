import SwiftUI

struct GradientColoredText<Text: View>: View {
    let colors: [Color]
    let text: Text

    init(
        colors: [Color] = [Color(.appOrange), Color(.appPurple)],
        @ViewBuilder text: () -> Text
    ) {
        self.colors = colors
        self.text = text()
    }

    var body: some View {
        text
            .overlay {
                LinearGradient(
                    colors: colors,
                    startPoint: .leading,
                    endPoint: .trailing
                )
                .mask {
                    text
                }
            }
    }
}
