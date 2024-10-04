package app.futured.kmptemplate.feature.ui.second

import app.futured.kmptemplate.feature.navigation.signedin.tab.b.TabBNavigator
import app.futured.kmptemplate.util.arch.SharedViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.core.annotation.Factory

@Factory
internal class SecondViewModel(
    private val navigator: TabBNavigator,
) : SharedViewModel<SecondViewState, Nothing>(),
    SecondScreen.Actions {
    override val viewState: MutableStateFlow<SecondViewState> = MutableStateFlow(SecondViewState())

    override fun onBack() = navigator.pop()

    override fun onNext() = navigator.navigateToThird()
}
