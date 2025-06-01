import SwiftUI

struct SizePreferenceKey: PreferenceKey {
    static var defaultValue: CGSize = .zero

    static func reduce(value: inout CGSize, nextValue: () -> CGSize) { }
}

extension View {
    func readSize(ignoreSafeArea: Bool = false, onChange: @escaping (CGSize) -> Void) -> some View {
        background(
            GeometryReader { geometry in
                Color.clear.preference(key: SizePreferenceKey.self, value: size(of: geometry, ignoreSafeArea: ignoreSafeArea))
            }
        )
        .onPreferenceChange(SizePreferenceKey.self, perform: onChange)
    }

    private func size(of geometry: GeometryProxy, ignoreSafeArea: Bool) -> CGSize {
        var size = geometry.size
        if ignoreSafeArea {
            size.width -= geometry.safeAreaInsets.leading + geometry.safeAreaInsets.trailing
            size.height -= geometry.safeAreaInsets.top + geometry.safeAreaInsets.bottom
        }
        return size
    }
}
