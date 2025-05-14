package app.futured.kmptemplate.feature.ui.formScreen

import kotlinx.coroutines.flow.StateFlow

/**
 * A copy-paste Form for creating new screen.
 * All you need to do is to copy contents of this file into your destination package and replace "Form" with name of the screen.
 *
 * Hint: Ctrl+G
 */
interface FormScreen {
    val viewState: StateFlow<FormViewState>
    val actions: Actions

    interface Actions {
        fun onBack()
        fun onFirstNameChange(string: String)
        fun onSurnameChange(string: String)
        fun onEmailChange(string: String)
        fun onPhoneChange(string: String)
        fun onPasswordChange(string: String)
        fun onNext()
    }
}
