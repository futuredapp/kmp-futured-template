package app.futured.kmptemplate.feature.navigation.cmp

import app.futured.kmptemplate.feature.ui.formScreen.FormScreen
import app.futured.kmptemplate.feature.ui.interopCheck.InteropCheckScreen
import com.arkivanov.decompose.Child
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable

interface CmpNavHost : BackHandlerOwner {

    val stack: StateFlow<ChildStack<CmpConfig, CmpChild>>
    val actions: Actions

    interface Actions {
        fun navigate(newStack: List<Child<CmpConfig, CmpChild>>)
        fun pop()
    }
}

@Serializable
sealed interface CmpConfig {

    @Serializable
    data object Form: CmpConfig

    @Serializable
    data object InteropCheck: CmpConfig

}

sealed interface CmpChild {

    data class Form(val screen: FormScreen) : CmpChild
    data class InteropCheck(val screen: InteropCheckScreen) : CmpChild
}
