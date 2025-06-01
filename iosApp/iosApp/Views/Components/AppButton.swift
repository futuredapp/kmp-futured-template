import SwiftUI

struct AppButton: View {
    @State private var contentSize: CGSize = .zero
    let image: Image?
    let text: String?
    let isLoading: Bool
    let isDisabled: Bool
    let action: () -> Void

    init(
        image: Image,
        isLoading: Bool = false,
        isDisabled: Bool = false,
        action: @escaping () -> Void
    ) {
        self.image = image
        self.text = nil
        self.isLoading = isLoading
        self.isDisabled = isDisabled
        self.action = action
    }

    init(
        text: String,
        isLoading: Bool = false,
        isDisabled: Bool = false,
        action: @escaping () -> Void
    ) {
        self.image = nil
        self.text = text
        self.isLoading = isLoading
        self.isDisabled = isDisabled
        self.action = action
    }

    var backgroundSize: CGSize {
        .init(width: max(0, contentSize.width - 9), height: max(0, contentSize.height - 9))
    }

    var backgroundCornerRadius: CGFloat {
        image == nil ? 40 : contentSize.width / 2
    }

    var rotationDegrees: Double {
        image == nil ? 0 : -15
    }

    var body: some View {
        Button(action: action) {
            ZStack {
                RoundedRectangle(cornerRadius: backgroundCornerRadius)
                    .fill(Color(.appPurple))
                    .frame(width: backgroundSize.width, height: backgroundSize.height)
                    .offset(x: 7, y: -5.5)
                    .blur(radius: 7)
                    .rotationEffect(.degrees(rotationDegrees))

                RoundedRectangle(cornerRadius: backgroundCornerRadius)
                    .fill(Color(.appOrange))
                    .frame(width: backgroundSize.width, height: backgroundSize.height)
                    .offset(x: -7, y: 5.5)
                    .blur(radius: 7)
                    .rotationEffect(.degrees(rotationDegrees))

                if let image {
                    image
                        .foregroundColor(.white)
                        .padding(26)
                        .background(
                            Circle()
                                .fill(Color(.appBlack))
                        )
                        .readSize { contentSize = $0 }
                        .opacity(isLoading ? 0 : 1)
                } else if let text {
                    Text(text)
                        .appFont(.description4, color: Color(.appWhite))
                        .padding(.horizontal, 24)
                        .padding(.vertical, 20)
                        .background(
                            RoundedRectangle(cornerRadius: 40)
                                .fill(Color(.appBlack))
                        )
                        .readSize { contentSize = $0 }
                        .opacity(isLoading ? 0 : 1)
                }
                LoadingView()
                    .background(
                        RoundedRectangle(cornerRadius: backgroundCornerRadius)
                            .fill(Color(.appBlack))
                            .frame(width: contentSize.width, height: contentSize.height)
                    )
                    .opacity(isLoading ? 1 : 0)
            }
        }
        .opacity(isDisabled ? 0.5 : 1)
        .disabled(isDisabled || isLoading)
    }
}

#Preview {
    ZStack {
        Color(.appBlack)
        VStack(spacing: 50) {
            AppButton(
                image: Image(.tabScan),
                isLoading: false,
                isDisabled: false
            ) {}
            AppButton(
                image: Image(.tabScan),
                isLoading: true,
                isDisabled: false
            ) {}
        }
    }
}
