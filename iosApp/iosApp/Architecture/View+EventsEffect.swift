import shared
import SwiftUI

extension View {
    /**
     This modifier allows views to collect events from shared KMP screens.
     
     It starts an async task which starts consuming events provided by `eventsFlow` and binds it to the view lifecycle.
     During execution of this task, each event produced from KMP is emitted in `onEvent` lambda for user to consume
     in the View.
     
     - Parameter eventsFlow: A Kotlin Flow adapter obtained from shared KMP screen.
     - Parameter onEvent: A lambda invoked each time an Event is produced from KMP screen.
     */
    @ViewBuilder
    func eventsEffect<SharedEvent>(
        for eventsFlow: SkieSwiftFlow<SharedEvent>,
        onEvent: @escaping (SharedEvent) -> Void
    ) -> some View {
        task {
            for await event in eventsFlow {
                onEvent(event)
            }
        }
    }
}
