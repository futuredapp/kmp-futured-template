package app.futured.kmptemplate.feature.ui.secondScreen

import app.futured.arkitekt.decompose.ext.update
import app.futured.factorygenerator.annotation.GenerateFactory
import app.futured.kmptemplate.feature.domain.GenerateAvatarUseCase
import app.futured.kmptemplate.feature.domain.GetStylesUseCase
import app.futured.kmptemplate.feature.model.ui.AvatarStyle
import app.futured.kmptemplate.feature.tool.toUiError
import app.futured.kmptemplate.feature.ui.base.AppComponentContext
import app.futured.kmptemplate.feature.ui.base.ScreenComponent
import app.futured.kmptemplate.feature.ui.dialog.errorDialog
import co.touchlab.kermit.Logger
import com.arkivanov.essenty.lifecycle.doOnCreate
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

@GenerateFactory
@Factory
internal class SecondComponent(
    @InjectedParam componentContext: AppComponentContext,
    @InjectedParam override val navigation: SecondScreenNavigation,
    private val getStylesUseCase: GetStylesUseCase,
    private val generateAvatarUseCase: GenerateAvatarUseCase,
) : ScreenComponent<SecondViewState, Nothing, SecondScreenNavigation>(
    componentContext = componentContext,
    defaultState = SecondViewState(),
),
    SecondScreen,
    SecondScreenNavigation by navigation,
    SecondScreen.Actions {

    private val logger = Logger.withTag("SecondComponent")

    init {
        doOnCreate {
            getStyles()
        }
    }

    override val viewState: StateFlow<SecondViewState> = componentState
    override val actions: SecondScreen.Actions = this

    override fun onCancel() {
        pop()
    }

    override fun onGenerate() {
        val styleId = viewState.value.selectedStyle?.id ?: return
        generateAvatarUseCase.execute(GenerateAvatarUseCase.Args(styleId)) {
            onStart {
                update(componentState) {
                    copy(isGenerating = true)
                }
            }
            onSuccess {
                pop()
            }
            onError { e ->
                update(componentState) {
                    copy(
                        isGenerating = false,
                        uiDialog = errorDialog(
                            error = e.toUiError(),
                            onConfirm = ::dismissDialog,
                        ),
                    )
                }
            }
        }
    }

    override fun onRetry() {
        getStyles()
    }

    override fun onStyle(style: AvatarStyle) {
        update(componentState) {
            copy(selectedStyle = style)
        }
    }

    private fun getStyles() {
        getStylesUseCase.execute {
            onStart {
                update(componentState) {
                    copy(isLoading = true)
                }
            }
            onSuccess {

                update(componentState) {
                    copy(isLoading = false, styles = it, error = null, selectedStyle = it.first())
                }
            }
            onError { e ->
                logger.e { "Get styles error: ${e.message}" }

                update(componentState) {
                    copy(
                        isLoading = false,
                        error = e.toUiError(),
                    )
                }
            }
        }
    }

    private fun dismissDialog() {
        update(componentState) {
            copy(uiDialog = null)
        }
    }
}
