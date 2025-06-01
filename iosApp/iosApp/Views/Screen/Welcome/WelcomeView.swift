import KMP
import SwiftUI

struct WelcomeView<ViewModel: WelcomeViewModelProtocol>: View {
    private let viewModel: ViewModel

    init(_ viewModel: ViewModel) {
        self.viewModel = viewModel
    }

    var body: some View {
        StateView(state: viewModel.state) {
            wrappedContent
        }
        .eventsEffect(for: viewModel.events) { event in
            switch onEnum(of: event) {
            case .openSystemSettings(let event):
                UIApplication.shared.openSystemSettings()
            }
        }
    }

    private var wrappedContent: some View {
        ViewThatFits(in: .vertical) {
            content
            ScrollView(showsIndicators: false) {
                content
            }
        }
        .padding(.horizontal, 20)
        .appBackground(showColors: false)
    }

    private var content: some View {
        VStack(alignment: .leading, spacing: 0) {
            cameraIcon
            title
            VStack(spacing: 13) {
                divider
                row(
                    icon: Image(.icAiAvatar),
                    title: Localizable.onboarding_ai_avatar_title.localized,
                    description: Localizable.onboarding_ai_avatar_description.localized
                )
                divider
            }
            .padding(.top, 31)
            Spacer(minLength: 48)
            button
        }
    }

    private var cameraIcon: some View {
        Image(.icCamera)
            .resizable()
            .aspectRatio(contentMode: .fit)
            .frame(width: 140, height: 140)
            .background {
                imageBackground
            }
            .frame(maxWidth: .infinity, alignment: .center)
            .padding(.top, 26)
    }

    private var imageBackground: some View {
        Circle()
            .fill(AngularGradient(gradient: Gradient(colors: [Color(.appOrange), Color(.appPurple)]), center: .center))
            .blur(radius: 50)
    }

    private var title: some View {
        Text(Localizable.onboarding_camera_permission_title.localized)
            .appFont(.heading1)
            .padding(.top, 8)
    }

    private var divider: some View {
        Color(.appWhite)
            .opacity(0.2)
            .frame(height: 0.5)
            .frame(maxWidth: .infinity)
    }

    private var button: some View {
        AppButton(
            image: Image(systemName: "arrow.right"),
            isLoading: viewModel.isLoading,
            action: viewModel.askForCameraPermission
        )
        .frame(maxWidth: .infinity, alignment: .center)
    }

    private func row(icon: Image, title: String, description: String) -> some View {
        HStack(spacing: 20) {
            icon
                .resizable()
                .frame(width: 52, height: 52)
            VStack(alignment: .leading, spacing: 2) {
                Text(title)
                    .appFont(.heading2)
                Text(description)
                    .appFont(.description4)
            }
            .fixedSize(horizontal: false, vertical: true)
        }
        .frame(maxWidth: .infinity, alignment: .leading)
    }
}
