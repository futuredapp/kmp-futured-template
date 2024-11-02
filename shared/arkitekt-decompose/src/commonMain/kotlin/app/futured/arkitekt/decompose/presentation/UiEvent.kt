package app.futured.arkitekt.decompose.presentation

/**
 * One time event sent from Component to UI.
 *
 * Event is guaranteed to be delivered just once even if screen rotation or a similar
 * operation is in progress.
 *
 * TODO do we need ViewState generic argument?
 */
interface UiEvent<VS : ViewState>