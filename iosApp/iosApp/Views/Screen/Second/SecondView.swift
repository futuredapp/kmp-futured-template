import KMP
import SwiftUI

struct SecondView<ViewModel: SecondViewModelProtocol>: View {
    private let viewModel: ViewModel

    init(_ viewModel: ViewModel) {
        self.viewModel = viewModel
    }

    var body: some View {
        StateView(state: viewModel.state) {
            content
        }
    }

    private var content: some View {
            ZStack(alignment: .bottom) {
                Color(.appBlack)
                    .ignoresSafeArea(.all)

                VStack(spacing: 50) {
                    Text("Pick your style")
                        .appFont(.heading1)

                    if let selectedStyle = viewModel.selectedStyle {
                        CachedAsyncImage(id: selectedStyle.cacheId, url: selectedStyle.preview, contentMode: .fit)
                            .cornerRadius(8)
                            .padding(.horizontal, 20)
                            .overlay(alignment: .top) {
                                Text(Localizable.preview_title.localized)
                                    .appFont(.textBold)
                                    .padding(.horizontal, 12)
                                    .padding(.vertical, 6)
                                    .background(
                                        Capsule()
                                            .foregroundColor(Color(.appDarkGrey))
                                    )
                                    .offset(y: -15)
                            }
                            .padding(.top, 15)
                    }

                    ScrollView(.horizontal, showsIndicators: false) {
                        LazyHStack(spacing: 12) {
                            ForEach(viewModel.styles, id: \.id) { style in
                                AvatarStyleView(style: style, isSelected: style == viewModel.selectedStyle) {
                                    viewModel.select(style: style)
                                }
                                .frame(width: 78)
                            }
                        }
                        .padding(.horizontal, 20)
                    }
                    .frame(height: 102)
                }
                .padding(.bottom)
            }
            .toolbar {
                ToolbarItem(placement: .topBarTrailing) {
                    if viewModel.isLoading {
                        LoadingView()
                    } else {
                        Button(action: viewModel.generate) {
                            Text(Localizable.generate_title.localized)
                                .appFont(.heading2)
                        }
                    }
                }
            }
            .toolbarRole(.editor)
        }
}
