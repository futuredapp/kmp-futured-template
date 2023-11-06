package app.futured.kmptemplate.feature.ui.first

import app.futured.kmptemplate.feature.navigation.home.HomeDestination
import app.futured.kmptemplate.feature.navigation.home.HomeNavigation
import app.futured.kmptemplate.feature.ui.third.ThirdScreen
import app.futured.kmptemplate.util.arch.Navigator
import app.futured.kmptemplate.util.arch.SharedViewModel
import com.arkivanov.decompose.value.MutableValue

internal class FirstViewModel(
    private val arg: String,
    private val navigator: Navigator
) : SharedViewModel<FirstViewState, FirstEvent, Nothing>(),
    FirstScreen.Actions {
    override val viewState: MutableValue<FirstViewState> = MutableValue(FirstViewState(arg))

    override fun onBack() {
        // todo
    }

    override fun onNext() {
        navigator.push(HomeDestination.Second)
    }
}
