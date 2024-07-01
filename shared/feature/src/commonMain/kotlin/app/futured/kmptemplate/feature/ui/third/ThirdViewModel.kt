package app.futured.kmptemplate.feature.ui.third

import app.futured.kmptemplate.feature.navigation.signedin.tab.b.TabBNavigator
import app.futured.kmptemplate.util.arch.SharedViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.core.annotation.Factory

@Factory
internal class ThirdViewModel(
    private val navigator: TabBNavigator,
) : SharedViewModel<ThirdViewState, Nothing>(), ThirdScreen.Actions {

    override val viewState: MutableStateFlow<ThirdViewState> = MutableStateFlow(ThirdViewState())

    override fun onBack() = navigator.pop()
}
