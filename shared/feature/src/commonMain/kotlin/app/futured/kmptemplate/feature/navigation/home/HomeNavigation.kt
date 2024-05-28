package app.futured.kmptemplate.feature.navigation.home

import com.arkivanov.decompose.Child
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.essenty.backhandler.BackHandler
import kotlinx.coroutines.flow.StateFlow

interface HomeNavigation {
    val stack: StateFlow<ChildStack<HomeDestination, HomeEntry>>
    val actions: Actions
    val backHandler: BackHandler

    interface Actions {
        fun iosPopTo(newStack: List<Child<HomeDestination, HomeEntry>>)
        fun onBack()
    }
}
