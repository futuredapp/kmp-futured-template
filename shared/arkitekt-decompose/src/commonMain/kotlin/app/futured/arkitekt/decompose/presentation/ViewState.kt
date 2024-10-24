package app.futured.arkitekt.decompose.presentation

/**
 * Holds screen's UI State.
 * ViewState instance of particular Component is stored in its [SharedViewModel].
 */
interface ViewState

/**
 * Stateless instance of [ViewState]. Use, if your components does not need to hold any view state.
 */
data object Stateless : ViewState
