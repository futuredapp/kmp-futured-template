package app.futured.kmptemplate.feature.navigation.home

import app.futured.kmptemplate.feature.ui.firstScreen.FirstScreen
import app.futured.kmptemplate.feature.ui.secondScreen.SecondScreen
import app.futured.kmptemplate.feature.ui.thirdScreen.ThirdScreen
import app.futured.kmptemplate.feature.ui.thirdScreen.ThirdScreenArgs
import com.arkivanov.decompose.Child
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable

interface HomeNavHost : BackHandlerOwner {

    val stack: StateFlow<ChildStack<HomeConfig, HomeChild>>
    val actions: Actions

    interface Actions {
        fun navigate(newStack: List<Child<HomeConfig, HomeChild>>)
        fun pop()
    }
}

@Serializable
sealed interface HomeConfig {

    @Serializable
    data object First : HomeConfig

    @Serializable
    data object Second : HomeConfig

    @Serializable
    data class Third(val args: ThirdScreenArgs) : HomeConfig
}

sealed interface HomeChild {

    data class First(val screen: FirstScreen) : HomeChild
    data class Second(val screen: SecondScreen) : HomeChild
    data class Third(val screen: ThirdScreen) : HomeChild
}
