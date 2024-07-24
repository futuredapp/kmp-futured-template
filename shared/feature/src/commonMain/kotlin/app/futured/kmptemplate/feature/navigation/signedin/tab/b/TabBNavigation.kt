package app.futured.kmptemplate.feature.navigation.signedin.tab.b

import com.arkivanov.decompose.Child
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import kotlinx.coroutines.flow.StateFlow

interface TabBNavigation : BackHandlerOwner {

    val stack: StateFlow<ChildStack<TabBDestination, TabBNavEntry>>
    val actions: Actions

    interface Actions {
        fun iosPopTo(newStack: List<Child<TabBDestination, TabBNavEntry>>)
        fun onBack()
    }
}
