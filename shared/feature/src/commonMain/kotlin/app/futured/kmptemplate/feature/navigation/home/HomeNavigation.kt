package app.futured.kmptemplate.feature.navigation.home

import com.arkivanov.decompose.Child
import com.arkivanov.decompose.router.stack.ChildStack
import kotlinx.coroutines.flow.StateFlow

interface HomeNavigation {
    val stack: StateFlow<ChildStack<HomeDestination, HomeEntry>>
    val actions: Actions

    interface Actions {
        fun iosPopTo(newStack: List<Child<HomeDestination, HomeEntry>>)
    }
}
