package app.futured.kmptemplate.feature.ui.second

import app.futured.kmptemplate.feature.navigation.home.HomeDestination
import app.futured.kmptemplate.feature.navigation.home.HomeNavigator
import app.futured.kmptemplate.util.arch.SharedViewModel
import com.arkivanov.decompose.value.MutableValue
import kotlinx.coroutines.flow.MutableStateFlow

internal class SecondViewModel(
    private val navigator: HomeNavigator
) : SharedViewModel<SecondViewState, SecondEvent, Nothing>(),
    SecondScreen.Actions {
    override val viewState: MutableStateFlow<SecondViewState> = MutableStateFlow(SecondViewState())

    override fun onBack() {
        navigator.pop()
    }

    override fun onNext() {
        navigator.push(HomeDestination.Third)
    }
}
