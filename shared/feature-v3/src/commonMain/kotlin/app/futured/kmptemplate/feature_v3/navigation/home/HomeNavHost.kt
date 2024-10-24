package app.futured.kmptemplate.feature_v3.navigation.home

import app.futured.kmptemplate.feature_v3.ui.base.AppComponentContext
import app.futured.kmptemplate.feature_v3.ui.firstScreen.FirstComponent
import app.futured.kmptemplate.feature_v3.ui.firstScreen.FirstScreen
import app.futured.kmptemplate.feature_v3.ui.firstScreen.FirstScreenNavigationActions
import app.futured.kmptemplate.feature_v3.ui.secondScreen.SecondScreen
import com.arkivanov.decompose.Child
import com.arkivanov.decompose.router.stack.ChildStack
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

interface HomeNavHost {

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
}

sealed interface HomeChild {

    data class First(val screen: FirstScreen) : HomeChild

    data class Second(val screen: SecondScreen) : HomeChild
}

// TODO
internal object HomeChildFactory : KoinComponent {

    fun create(componentContext: AppComponentContext, navigation: FirstScreenNavigationActions): FirstComponent =
        FirstComponent(componentContext, navigation, get(), get())
}

//class KoinAppComponentFactory(val context: GenericComponentContext<*>) : KoinComponent {
//
//    inline fun <reified C : AppComponent<*, *>> createComponent(
//        vararg parameters: Any?,
//    ): C = get(
//        qualifier = null,
//        parameters = {
//            parametersOf(
//                context,
//                *parameters,
//            )
//        },
//    )
//}
