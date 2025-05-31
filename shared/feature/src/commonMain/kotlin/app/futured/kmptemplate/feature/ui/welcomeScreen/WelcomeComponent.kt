package app.futured.kmptemplate.feature.ui.welcomeScreen

import app.futured.arkitekt.decompose.ext.update
import app.futured.factorygenerator.annotation.GenerateFactory
import app.futured.kmptemplate.feature.ui.base.AppComponentContext
import app.futured.kmptemplate.feature.ui.base.ScreenComponent
import app.futured.kmptemplate.feature.ui.dialog.actionDialog
import app.futured.kmptemplate.resources.MR
import dev.icerock.moko.resources.desc.desc
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

@GenerateFactory
@Factory
internal class WelcomeComponent(
    @InjectedParam componentContext: AppComponentContext,
    @InjectedParam override val navigation: WelcomeScreenNavigation,
) : ScreenComponent<WelcomeViewState, WelcomeUIEvent, WelcomeScreenNavigation>(
    componentContext = componentContext,
    defaultState = WelcomeViewState(),
),
    WelcomeScreen,
    WelcomeScreenNavigation by navigation,
    WelcomeScreen.Actions {

    override val actions: WelcomeScreen.Actions = this
    override val viewState: StateFlow<WelcomeViewState> = componentState.asStateFlow()


    override fun onCameraPermissionGranted() {
        navigateToHome()
    }

    override fun onCameraPermissionDenied() {
        update(componentState) {
            copy(
                uiDialog = actionDialog(
                    title = MR.strings.onboarding_camera_required_dialog_title.desc(),
                    text = MR.strings.onboarding_camera_required_dialog_text.desc(),
                    onConfirm = {
                        sendUiEvent(WelcomeUIEvent.OpenSystemSettings)
                        dismissDialog()
                    },
                    onDismiss = ::dismissDialog,
                    confirmButton = MR.strings.settings.desc(),
                    negativeButton = MR.strings.cancel.desc(),
                ),
            )
        }
    }

    private fun dismissDialog() = update(componentState) {
        copy(uiDialog = null)
    }
}

