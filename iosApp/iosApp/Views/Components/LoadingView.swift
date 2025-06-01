import SwiftUI

struct LoadingView: View {
    let color: Color

    init(color: Color = Color(.appWhite)) {
        self.color = color
    }

    var body: some View {
        ProgressView()
            .progressViewStyle(.circular)
            .tint(Color(.appWhite))
    }
}
