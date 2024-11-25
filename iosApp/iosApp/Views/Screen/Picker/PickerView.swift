import shared
import SwiftUI

struct PickerView<ViewModel: PickerViewModelProtocol>: View {
    private let viewModel: ViewModel

    init(_ viewModel: ViewModel) {
        self.viewModel = viewModel
    }

    var body: some View {
        VStack {
            HStack {
                Text("Pick an item").font(.headline)
                Button("Close", action: viewModel.onDismiss)
            }
            if viewModel.isLoading {
                ProgressView()
            } else {
                List {
                    ForEach(viewModel.items) { item in
                        HStack {
                            Text(item.id)
                            Spacer()
                        }
                        .contentShape(Rectangle())
                        .onTapGesture {
                            viewModel.onPick(item: item)
                        }
                    }
                }
            }
        }
    }
}