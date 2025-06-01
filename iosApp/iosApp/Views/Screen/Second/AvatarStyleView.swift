import KMP
import SwiftUI

// AvatarStyleView, View that previews AvatarStyle struct, has rounded cordners, name label on the bottom and is the whole view is selectable
struct AvatarStyleView: View {
    let style: AvatarStyle
    let isSelected: Bool
    let onTap: () -> Void

    var body: some View {
        VStack(spacing: 0) {
            CachedAsyncImage(id: style.cacheId, url: style.preview)
                .frame(width: 76, height: 78)
                .onTapGesture {
                    onTap()
                }
            Text(style.name)
                .appFont(.textBold)
                .multilineTextAlignment(.center)
                .frame(maxWidth: .infinity)
                .padding(.vertical, 6)
                .background(barBackground)
        }
        .mask {
            RoundedRectangle(cornerRadius: 8)
        }
        .padding(1)
        .background(
            RoundedRectangle(cornerRadius: 8)
                .fill(linearGradient)
        )
        .onTapGesture {
            onTap()
        }
    }

    @ViewBuilder var barBackground: some View {
        if isSelected {
            LinearGradient(colors: [Color(.appOrange), Color(.appPurple)], startPoint: .leading, endPoint: .trailing)
        } else {
            Color(.appDark)
        }
    }

    private var linearGradient: LinearGradient {
        LinearGradient(
            gradient: Gradient(colors: [.white.opacity(0.32), .white.opacity(0.05)]),
            startPoint: .topLeading,
            endPoint: .bottomTrailing
        )
    }

    var placeholder: some View {
        LoadingView()
    }
}

#Preview {
    ZStack {
        Color(.appBlack)
        AvatarStyleView(style: AvatarStyle(id: 1, name: "Mars", preview: "https://storage.googleapis.com/mdevcamp-avatars/avatars/15545183-4de6-4c4b-9869-3a5fe528da83.png"), isSelected: false) {}
             .frame(width: 76, height: 102)
            .padding()
    }
}
