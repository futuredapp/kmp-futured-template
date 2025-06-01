import KMP
import SwiftUI

struct SecondView<ViewModel: SecondViewModelProtocol>: View {
    private let viewModel: ViewModel

    init(_ viewModel: ViewModel) {
        self.viewModel = viewModel
    }

    var body: some View {
        HStack {
        }
    }
}
