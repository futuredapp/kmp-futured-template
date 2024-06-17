package app.futured.kmptemplate.android.tools.arch

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import app.futured.kmptemplate.util.arch.Event
import kotlinx.coroutines.flow.Flow

/**
 * When [EventsEffect] enters composition, it will start observing the event flow from provided [eventsFlow].
 * Each collected event goes through [observer] lambda which can be used to react to a specific event.
 * Use the [onEvent] function to filter out the event you are interested in.
 *
 * Example usage:
 * ```kotlin
 * EventsEffect(screen.events) {
 *      onEvent<MainScreenEvents.ShowAndroidToast> { event ->
 *          println("Do something with the $event")
 *          ...
 *      }
 * }
 * ```
 *
 * @param eventsFlow Flow of events from Component.
 * @param observer Event receiver lambda.
 */
@Composable
inline fun <reified E : Event<*>> EventsEffect(
    eventsFlow: Flow<E>,
    crossinline observer: suspend E.() -> Unit,
) {
    LaunchedEffect(eventsFlow) {
        eventsFlow.collect {
            observer(it)
        }
    }
}

inline fun <reified E : Event<*>> Event<*>.onEvent(action: (E) -> Unit) {
    if (this is E) {
        action(this)
    }
}
