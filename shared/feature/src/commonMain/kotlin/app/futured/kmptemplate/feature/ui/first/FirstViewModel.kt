package app.futured.kmptemplate.feature.ui.first

import app.futured.kmptemplate.feature.navigation.home.HomeDestination
import app.futured.kmptemplate.feature.navigation.home.HomeNavigator
import app.futured.kmptemplate.util.arch.SharedViewModel
import com.arkivanov.decompose.value.MutableValue

internal class FirstViewModel(
    arg: String,
    private val navigator: HomeNavigator
) : SharedViewModel<FirstViewState, FirstEvent, Nothing>(),
    FirstScreen.Actions {
    override val viewState: MutableValue<FirstViewState> = MutableValue(FirstViewState(arg))

    override fun onBack() {
        navigator.pop()
    }

    override fun onNext() {
        navigator.push(HomeDestination.Second)
    }
}
