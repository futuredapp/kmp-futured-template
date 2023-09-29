package app.futured.kmptemplate.feature.navigation.root

import app.futured.kmptemplate.util.arch.SharedViewModel
import com.arkivanov.decompose.value.MutableValue

internal class RootNavigationViewModel :
    SharedViewModel<RootNavigationViewState, RootNavigationEvent, Nothing>() {
    override val viewState: MutableValue<RootNavigationViewState> =
        MutableValue(RootNavigationViewState())
}
