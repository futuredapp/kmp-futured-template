package app.futured.kmptemplate.feature.ui.second

import app.futured.kmptemplate.feature.navigation.home.HomeDestination
import app.futured.kmptemplate.feature.navigation.home.HomeStackNavigator
import app.futured.kmptemplate.util.arch.SharedViewModel
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import kotlinx.coroutines.flow.MutableStateFlow

internal class SecondViewModel(
    private val navigator: HomeStackNavigator
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
