import SwiftUI

/// Represents alert presentation state.
///
/// Use as an optional property (`var alert: AlertModel?`) and present
/// with `.alert(isPresented:presenting:)`. Set to `nil` to dismiss.
struct AlertModel: Identifiable {
    let id = UUID()
    let message: String
}

extension Binding where Value == AlertModel? {
    /// Converts an optional `AlertModel` binding to a `Bool` binding
    /// suitable for SwiftUI's `.alert(isPresented:)` modifier.
    /// Setting to `false` dismisses by nilling out the model.
    var isPresented: Binding<Bool> {
        Binding<Bool>(
            get: { wrappedValue != nil },
            set: {
                if !$0 {
                    wrappedValue = nil
                }
            }
        )
    }
}
