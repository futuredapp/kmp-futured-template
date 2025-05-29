package app.futured.kmptemplate.feature.ui.formScreen

import app.futured.arkitekt.decompose.ext.update
import app.futured.factorygenerator.annotation.GenerateFactory
import app.futured.kmptemplate.feature.data.model.AlertDialogUi
import app.futured.kmptemplate.feature.ui.base.AppComponentContext
import app.futured.kmptemplate.feature.ui.base.ScreenComponent
import app.futured.kmptemplate.resources.MR
import dev.icerock.moko.resources.desc.desc
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

@Factory
@GenerateFactory
internal class FormComponent(
    @InjectedParam componentContext: AppComponentContext,
    @InjectedParam override val navigation: FormScreenNavigation,
) : ScreenComponent<FormViewState, Nothing, FormScreenNavigation>(
    componentContext = componentContext,
    defaultState = FormViewState(),
),
    FormScreen,
    FormScreenNavigation by navigation,
    FormScreen.Actions {

    override val actions: FormScreen.Actions = this
    override val viewState: StateFlow<FormViewState> = componentState.asStateFlow()

    override fun onBack() = pop()

    override fun onFirstNameChange(string: String) {
        update(componentState){
            copy(firstName = string)
        }
    }

    override fun onSurnameChange(string: String) {
        update(componentState){
            copy(surname = string)
        }
    }

    override fun onEmailChange(string: String) {
        update(componentState){
            copy(email = string)
        }
    }

    override fun onPhoneChange(string: String) {
        update(componentState){
            copy(phone = string)
        }
    }

    override fun onPasswordChange(string: String) {
        update(componentState){
            copy(password = string)
        }
    }

    override fun onNext() {
        navigateToInteropCheck()
    }

    override fun onShowAlertDialog() {
        update(componentState){
            copy(
                alertDialogUi = AlertDialogUi(
                    title= MR.strings.form_alert_title.desc(),
                    message = MR.strings.form_alert_message.desc(),
                    positiveButton = MR.strings.form_alert_positive_button.desc(),
                    dismissButton = MR.strings.form_alert_dismiss_button.desc(),
                    onDismiss = {
                        update(componentState){
                            copy(alertDialogUi = null)
                        }
                    },
                    onPositiveClick = {
                        update(componentState){
                            copy(alertDialogUi = null)
                        }
                    }
                )
            )
        }
    }

}
