package app.futured.kmptemplate.feature.navigation.home

import app.futured.kmptemplate.feature.ui.first.FirstComponent
import app.futured.kmptemplate.feature.ui.first.FirstScreen
import app.futured.kmptemplate.feature.ui.second.SecondComponent
import app.futured.kmptemplate.feature.ui.second.SecondScreen
import app.futured.kmptemplate.feature.ui.third.ThirdComponent
import app.futured.kmptemplate.feature.ui.third.ThirdScreen
import app.futured.kmptemplate.util.arch.Component
import app.futured.kmptemplate.util.arch.Destination
import app.futured.kmptemplate.util.arch.NavigationComponentContext
import app.futured.kmptemplate.util.arch.Navigator
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandler
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.essenty.statekeeper.StateKeeper

interface HomeNavigation {
    val stack: Value<ChildStack<HomeDestination<Component>, Component>>
}

sealed class HomeDestination<out C: Component> : Parcelable, Destination<C> {
    @Parcelize
    data class First(private val arg: String) : HomeDestination<FirstScreen>() {
        override fun createComponent(componentContext: ComponentContext): FirstScreen =
            FirstComponent(componentContext, arg)
    }

    @Parcelize
    data object Second : HomeDestination<SecondScreen>() {
        override fun createComponent(componentContext: ComponentContext): SecondScreen {
            return SecondComponent(componentContext)
        }
    }

    @Parcelize
    data object Third : HomeDestination<ThirdScreen>() {
        override fun createComponent(componentContext: ComponentContext): ThirdScreen {
            return ThirdComponent(componentContext)
        }
    }
}

//interface Sheet<ARG, Component> : Destination<ARG, Component> {
//
//}
//
//interface Dialog<ARG, Component>: Destination<ARG, Component> {
//
//}

// can be generated
//class ComponentFactory {
//    fun <ARG,COMPONENT> createComponent(config: Destination<ARG>, componentContext: ComponentContext): COMPONENT {
//        COMPONENT.create(config.arg, componentContext)
//    }
//}

//class Router(componentContext: ComponentContext) : NavigationComponentContext {
//
//}

class HomeNavigator(componentContext: ComponentContext): Navigator<HomeDestination<Component>>, ComponentContext by componentContext{
    private val stackNavigator = StackNavigation<HomeDestination<Component>>()

    val stack: Value<ChildStack<HomeDestination<Component>, Component>> = childStack(
        source = stackNavigator,
        key = HomeNavigationComponent::class.simpleName.toString(),
        initialStack = { listOf(HomeDestination.First("some")) },
        handleBackButton = true,
        childFactory = { configuration, childContext ->
            configuration.createComponent(childContext)
        }
    )

    override fun push(destination: HomeDestination<Component>) {
        stackNavigator.push(destination)
    }

    override fun pop() {
        stackNavigator.pop()
    }
}
