import SwiftUI

struct AvatarPlaceholderView: View {
    var body: some View {
        Image(systemName: "person.crop.circle.fill")
            .resizable()
            .foregroundStyle(photoGradient)
            .aspectRatio(contentMode: .fill)
            .clipShape(Circle())
    }

    private var photoGradient: LinearGradient {
        LinearGradient(
            gradient: Gradient(colors: [Color(.white).opacity(0.30), .clear]),
            startPoint: .topLeading,
            endPoint: .bottomTrailing
        )
    }
}
