package app.futured.kmptemplate.feature.navigation.signedin.tab.b

import co.touchlab.kermit.Logger
import com.arkivanov.decompose.Child
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.essenty.lifecycle.Lifecycle
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class TabBNavigationComponent(
    componentContext: ComponentContext,
    initialStack: List<TabBDestination>,
) : TabBNavigation,
    TabBNavigation.Actions,
    ComponentContext by componentContext,
    KoinComponent {

    private val navigator: TabBNavigator by inject()

    override val stack: StateFlow<ChildStack<TabBDestination, TabBNavEntry>> = navigator.createStack(
        componentContext = componentContext,
        initialStack = initialStack,
    )

    override val actions: TabBNavigation.Actions = this

    override fun iosPopTo(newStack: List<Child<TabBDestination, TabBNavEntry>>) = navigator.iosPop(newStack)
    override fun onBack() = navigator.pop()

    init {
        lifecycle.subscribe(object : Lifecycle.Callbacks {
            override fun onCreate() {
                Logger.withTag("Tab B").d { "onCreate" }
            }

            override fun onStart() {
                Logger.withTag("Tab B").d { "onStart" }
            }

            override fun onResume() {
                Logger.withTag("Tab B").d { "onResume" }
            }

            override fun onPause() {
                Logger.withTag("Tab B").d { "onPause" }
            }

            override fun onStop() {
                Logger.withTag("Tab B").d { "onStop" }
            }

            override fun onDestroy() {
                Logger.withTag("Tab B").d { "onDestroy" }
            }
        })
    }
}
