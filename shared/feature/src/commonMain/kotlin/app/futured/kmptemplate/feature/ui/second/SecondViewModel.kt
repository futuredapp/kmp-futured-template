package app.futured.kmptemplate.feature.ui.second

import app.futured.kmptemplate.feature.navigation.home.HomeDestination
import app.futured.kmptemplate.feature.navigation.home.HomeNavigator
import app.futured.kmptemplate.util.arch.SharedViewModel
import com.arkivanov.decompose.value.MutableValue

internal class SecondViewModel(
    private val navigator: HomeNavigator
) : SharedViewModel<SecondViewState, SecondEvent, Nothing>(),
    SecondScreen.Actions {
    override val viewState: MutableValue<SecondViewState> = MutableValue(SecondViewState())

    override fun onBack() {
        navigator.pop()
    }

    override fun onNext() {
        navigator.push(HomeDestination.Third)
    }
}
