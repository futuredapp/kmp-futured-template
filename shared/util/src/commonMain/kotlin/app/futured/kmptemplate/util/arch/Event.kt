package app.futured.kmptemplate.util.arch

/**
 * One time event sent from ViewModel to Component.
 *
 * Event is guaranteed to be delivered just once even if screen rotation or a similar
 * operation is in progress.
 */
interface UiEvent<VS : ViewState>
interface OutputEvent<VS : ViewState>
