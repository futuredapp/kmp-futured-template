package app.futured.kmptemplate.feature.ui.picker

import app.futured.kmptemplate.feature.ui.base.AppComponentContext
import app.futured.kmptemplate.feature.ui.base.ScreenComponent
import app.futured.kmptemplate.resources.MR
import com.arkivanov.essenty.lifecycle.doOnCreate
import dev.icerock.moko.resources.desc.desc
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import kotlin.time.Duration.Companion.seconds

@Factory
internal class VegetablePickerComponent(
    @InjectedParam componentContext: AppComponentContext,
    @InjectedParam override val navigation: PickerNavigation,
) : ScreenComponent<PickerState, Nothing, PickerNavigation>(componentContext, PickerState()),
    Picker {

    override val actions: Picker.Actions = object : Picker.Actions {
        override fun onPick(item: String) = navigation.dismiss(item)
        override fun onDismiss() = navigation.dismiss(null)
    }

    override val viewState: StateFlow<PickerState> = componentState.asStateFlow()

    init {
        doOnCreate {
            launchWithHandler {
                componentState.update { it.copy(isLoading = true) }
                delay(1.seconds)
                componentState.update {
                    it.copy(
                        isLoading = false,
                        items = persistentListOf(
                            MR.strings.veggie_carrot.desc(),
                            MR.strings.veggie_pepper.desc(),
                            MR.strings.veggie_onion.desc(),
                            MR.strings.veggie_chilli.desc(),
                        ),
                    )
                }
            }
        }
    }
}
