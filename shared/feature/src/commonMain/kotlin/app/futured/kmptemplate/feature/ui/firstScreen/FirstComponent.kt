package app.futured.kmptemplate.feature.ui.firstScreen

import app.futured.arkitekt.decompose.ext.update
import app.futured.factorygenerator.annotation.GenerateFactory
import app.futured.kmptemplate.feature.domain.GetAvatarsUseCase
import app.futured.kmptemplate.feature.domain.GetLocalPhotoUseCase
import app.futured.kmptemplate.feature.domain.LoadPhotoUseCase
import app.futured.kmptemplate.feature.domain.UploadPhotoUseCase
import app.futured.kmptemplate.feature.model.ui.Avatar
import app.futured.kmptemplate.feature.model.ui.AvatarStatus
import app.futured.kmptemplate.feature.tool.UiError
import app.futured.kmptemplate.feature.tool.toUiError
import app.futured.kmptemplate.feature.ui.base.AppComponentContext
import app.futured.kmptemplate.feature.ui.base.ScreenComponent
import app.futured.kmptemplate.feature.ui.dialog.actionDialog
import app.futured.kmptemplate.feature.ui.dialog.errorDialog
import app.futured.kmptemplate.resources.MR
import co.touchlab.kermit.Logger
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnStart
import dev.icerock.moko.resources.desc.desc
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

@Factory
@GenerateFactory
internal class FirstComponent(
    @InjectedParam componentContext: AppComponentContext,
    @InjectedParam override val navigation: FirstScreenNavigation,
    private val getAvatarsUseCase: GetAvatarsUseCase,
    private val uploadPhotoUseCase: UploadPhotoUseCase,
    private val getLocalPhotoUseCase: GetLocalPhotoUseCase,
    private val loadPhotoUseCase: LoadPhotoUseCase,
) : ScreenComponent<FirstViewState, FirstUiEvent, FirstScreenNavigation>(
    componentContext = componentContext,
    defaultState = FirstViewState(),
),
    FirstScreen,
    FirstScreenNavigation by navigation,
    FirstScreen.Actions {

    private val logger = Logger.withTag("FirstComponent")

    override val viewState: StateFlow<FirstViewState> = componentState.asStateFlow()
    override val actions: FirstScreen.Actions = this

    init {
        doOnCreate {
            loadPhoto()
            getLocalPhoto()
        }

        doOnStart {
            getAvatars()
        }
    }



    override fun onCreateNewAvatar() {
        navigateToSecond()
    }

    override fun onAvatar(avatar: Avatar) {
        when (avatar.status) {
            AvatarStatus.InProgress -> {}
            AvatarStatus.Done -> navigateToThird(avatar)
            AvatarStatus.Failed -> {
                update(componentState) {
                    copy(
                        uiDialog = errorDialog(
                            error = UiError(
                                title = MR.strings.avatar_generation_failed_title.desc(),
                                message = MR.strings.avatar_generation_failed_description.desc(),
                            ),
                            onConfirm = ::dismissDialog,
                        ),
                    )
                }
            }
        }
    }

    override fun onCameraPermissionDenied() {
        update(componentState) {
            copy(
                uiDialog = actionDialog(
                    title = MR.strings.onboarding_camera_required_dialog_title.desc(),
                    text = MR.strings.onboarding_camera_required_dialog_text.desc(),
                    onConfirm = {
                        sendUiEvent(FirstUiEvent.OpenSystemSettings)
                        dismissDialog()
                    },
                    onDismiss = ::dismissDialog,
                    confirmButton = MR.strings.settings.desc(),
                    negativeButton = MR.strings.cancel.desc(),
                ),
            )
        }
    }

    override fun onRetry() {
        update(componentState) {
            copy(isLoading = true)
        }
        getAvatars()
    }

    override fun onRetakePhoto(imageName: String, imageData: ByteArray) {
        uploadPhotoUseCase.execute(UploadPhotoUseCase.Args(imageName, imageData)) {
            onStart {
                update(componentState) {
                    copy(isPhotoLoading = true)
                }
            }
            onSuccess {
                update(componentState) {
                    copy(isPhotoLoading = false, photo = it)
                }
            }
            onError { e ->
                logger.e { "Upload photo error: ${e.message}" }

                update(componentState) {
                    copy(
                        isPhotoLoading = false,
                        uiDialog = errorDialog(
                            error = e.toUiError(),
                            onConfirm = ::dismissDialog,
                        ),
                    )
                }
            }
        }
    }

    private fun loadPhoto() {
        loadPhotoUseCase.execute {
            onStart {
                update(componentState) {
                    copy(isPhotoLoading = true)
                }
            }
            onSuccess {
                update(componentState) {
                    copy(isPhotoLoading = false)
                }
            }
            onError { e ->
                logger.e { "Upload photo error: ${e.message}" }

                update(componentState) {
                    copy(
                        isPhotoLoading = false,
                        uiDialog = errorDialog(
                            error = e.toUiError(),
                            onConfirm = ::dismissDialog,
                        ),
                    )
                }
            }
        }
    }
    private fun getLocalPhoto() {
        getLocalPhotoUseCase.execute {
            onNext {
                update(componentState) {
                    copy(photo = it)
                }
            }
            onError {
                logger.e { "Get local photo error: ${it.message}" }
            }
        }
    }

    private fun dismissDialog() = update(componentState) {
        copy(uiDialog = null)
    }

    private fun getAvatars() {
        getAvatarsUseCase.execute {
            onSuccess {
                update(componentState) {
                    copy(avatars = it, isLoading = false, error = null)
                }
            }
            onError { e ->
                logger.e { "Get avatars error: ${e.message}" }

                update(componentState) {
                    copy(isLoading = false, error = e.toUiError())
                }
            }
        }
    }

}
