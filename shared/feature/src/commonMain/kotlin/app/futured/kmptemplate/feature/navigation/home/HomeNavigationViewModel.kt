package app.futured.kmptemplate.feature.navigation.home

import app.futured.kmptemplate.util.arch.SharedViewModel
import com.arkivanov.decompose.value.MutableValue

internal class HomeNavigationViewModel :
    SharedViewModel<HomeNavigationViewState, HomeNavigationEvent, Nothing>() {
    override val viewState: MutableValue<HomeNavigationViewState> = MutableValue(
        HomeNavigationViewState()
    )
}
